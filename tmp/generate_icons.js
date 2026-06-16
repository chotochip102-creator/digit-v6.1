const fs = require('fs');
const path = require('path');
const { execSync } = require('child_process');

// Ensure sharp is installed locally in a temporary directory or in the root to perform the rendering
console.log('Installing sharp library for icon rendering...');
try {
    execSync('npm install sharp', { stdio: 'inherit' });
} catch (e) {
    console.error('Failed to install sharp locally, attempting to run with npx...');
}

const sharp = require('sharp');

const svgCode = `
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 108 108" width="108" height="108">
  <!-- Subtle circular/squircle clean modern backdrop as suggested by design guidelines -->
  <rect width="108" height="108" rx="24" fill="#ffffff" />
  
  <g transform="translate(4, 4) scale(0.92)">
    <!-- Floating Lavender dot representing student / knowledge -->
    <circle cx="50" cy="18" r="7.5" fill="#BFC2FE" />
    
    <g>
      <!-- LEFT PAGE (Deep Indigo) -->
      <path d="M 48 31 C 38 28.5, 28 26.5, 17 26.5 C 15 26.5, 13 28.5, 13 30.5 L 13 69.5 C 13 71.5, 15 73.5, 17 73.5 C 28 73.5, 38 75.5, 48 78 Z" fill="#54578C" />
      
      <!-- LEFT PAGE LINES -->
      <rect x="21" y="38" width="16" height="3" rx="1.5" fill="#BFC2FE" opacity="0.8" />
      <rect x="21" y="47" width="16" height="3" rx="1.5" fill="#BFC2FE" opacity="0.8" />
      <rect x="21" y="56" width="16" height="3" rx="1.5" fill="#BFC2FE" opacity="0.8" />
      
      <!-- RIGHT PAGE (Teal) -->
      <path d="M 52 31 C 62 28.5, 72 26.5, 83 26.5 C 85 26.5, 87 28.5, 87 30.5 L 87 69.5 C 87 71.5, 85 73.5, 83 73.5 C 72 73.5, 62 75.5, 52 78 Z" fill="#1A5F7A" />
      
      <!-- PLAY ICON ON RIGHT PAGE -->
      <polygon points="63,42 63,62 77,52" fill="#ffffff" />
    </g>
  </g>
</svg>
`;

const targets = [
    { dir: 'app/src/main/res/drawable', file: 'ic_app_logo_foreground.png', size: 512 },
    { dir: 'app/src/main/res/drawable', file: 'ic_launcher_foreground.png', size: 512 },
    { dir: 'app/src/main/res/mipmap-mdpi', file: 'ic_launcher.png', size: 48 },
    { dir: 'app/src/main/res/mipmap-hdpi', file: 'ic_launcher.png', size: 72 },
    { dir: 'app/src/main/res/mipmap-xhdpi', file: 'ic_launcher.png', size: 96 },
    { dir: 'app/src/main/res/mipmap-xxhdpi', file: 'ic_launcher.png', size: 144 },
    { dir: 'app/src/main/res/mipmap-xxxhdpi', file: 'ic_launcher.png', size: 192 }
];

async function generate() {
    for (const target of targets) {
        const destDir = path.join('/', target.dir);
        if (!fs.existsSync(destDir)) {
            fs.mkdirSync(destDir, { recursive: true });
        }
        const destPath = path.join(destDir, target.file);
        console.log(`Generating: ${destPath} (${target.size}x${target.size})`);
        
        await sharp(Buffer.from(svgCode))
            .resize(target.size, target.size)
            .png()
            .toFile(destPath);
    }
    console.log('All icons generated successfully!');
}

generate().catch(err => {
    console.error('Error during generation:', err);
    process.exit(1);
});
