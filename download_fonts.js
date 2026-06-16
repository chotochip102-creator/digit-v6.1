const https = require('https');
const fs = require('fs');
const path = require('path');

const fonts = [
  { url: 'https://github.com/google/fonts/raw/main/ofl/poppins/Poppins-Regular.ttf', dest: 'poppins_regular.ttf' },
  { url: 'https://github.com/google/fonts/raw/main/ofl/poppins/Poppins-Medium.ttf', dest: 'poppins_medium.ttf' },
  { url: 'https://github.com/google/fonts/raw/main/ofl/poppins/Poppins-SemiBold.ttf', dest: 'poppins_semibold.ttf' },
  { url: 'https://github.com/google/fonts/raw/main/ofl/poppins/Poppins-Bold.ttf', dest: 'poppins_bold.ttf' },
  { url: 'https://github.com/google/fonts/raw/main/ofl/hindsiliguri/HindSiliguri-Regular.ttf', dest: 'hind_siliguri_regular.ttf' },
  { url: 'https://github.com/google/fonts/raw/main/ofl/hindsiliguri/HindSiliguri-Medium.ttf', dest: 'hind_siliguri_medium.ttf' },
  { url: 'https://github.com/google/fonts/raw/main/ofl/hindsiliguri/HindSiliguri-SemiBold.ttf', dest: 'hind_siliguri_semibold.ttf' },
  { url: 'https://github.com/google/fonts/raw/main/ofl/hindsiliguri/HindSiliguri-Bold.ttf', dest: 'hind_siliguri_bold.ttf' }
];

const fontDir = 'app/src/main/res/font';
if (!fs.existsSync(fontDir)) {
  fs.mkdirSync(fontDir, { recursive: true });
}

function download(url, dest) {
  return new Promise((resolve, reject) => {
    https.get(url, (res) => {
      // Handle redirects
      if (res.statusCode >= 300 && res.statusCode < 400 && res.headers.location) {
          download(res.headers.location, dest).then(resolve).catch(reject);
          return;
      }
      
      const file = fs.createWriteStream(path.join(fontDir, dest));
      res.pipe(file);
      file.on('finish', () => {
        file.close();
        resolve();
      });
    }).on('error', (err) => {
      fs.unlink(path.join(fontDir, dest), () => {});
      reject(err);
    });
  });
}

async function run() {
  for (const font of fonts) {
    console.log(`Downloading ${font.dest}...`);
    try {
      await download(font.url, font.dest);
      console.log(`Successfully downloaded ${font.dest}`);
    } catch (e) {
      console.error(`Failed to download ${font.dest}:`, e);
    }
  }
}

run();
