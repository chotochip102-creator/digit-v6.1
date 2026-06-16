const { execSync } = require('child_process');
try {
    execSync('npm install sharp', { stdio: 'inherit' });
} catch (e) {
    console.error(e);
}
const sharp = require('sharp');
const fs = require('fs');

async function fixIcons() {
    const inputFile = 'app/src/main/res/drawable/ic_app_logo_foreground.png';
    const bgFile = 'app/src/main/res/drawable/ic_launcher_background.xml';
    
    // Ensure we have a valid background
    if (!fs.existsSync(bgFile)) {
        fs.writeFileSync(bgFile, `<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="108dp"
    android:height="108dp"
    android:viewportWidth="108"
    android:viewportHeight="108">
    <path
        android:fillColor="#FFFFFF"
        android:pathData="M0,0h108v108h-108z" />
</vector>`);
    }

    try {
        console.log('Processing user uploaded icon...');
        
        // Generate proper foreground
        await sharp(inputFile)
            .resize(512, 512, { fit: 'contain', background: { r: 255, g: 255, b: 255, alpha: 0 } })
            .png()
            .toFile('app/src/main/res/drawable/ic_launcher_foreground.png');

        // Copy directly to mipmaps as fallback
        const sizes = {
            'mdpi': 48,
            'hdpi': 72,
            'xhdpi': 96,
            'xxhdpi': 144,
            'xxxhdpi': 192
        };

        for (const [dpi, size] of Object.entries(sizes)) {
            const dir = `app/src/main/res/mipmap-${dpi}`;
            if (!fs.existsSync(dir)) fs.mkdirSync(dir, { recursive: true });
            
            await sharp(inputFile)
                .resize(size, size, { fit: 'contain', background: { r: 255, g: 255, b: 255, alpha: 0 } })
                .png()
                .toFile(`${dir}/ic_launcher.png`);
                
            await sharp(inputFile)
                .resize(size, size, { fit: 'contain', background: { r: 255, g: 255, b: 255, alpha: 0 } })
                .png()
                .toFile(`${dir}/ic_launcher_round.png`);
        }
        
        console.log('Successfully generated proper PNGs');
    } catch (e) {
        console.error('Error processing icon:', e);
    }
}

fixIcons();
