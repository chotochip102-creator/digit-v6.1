import os
import re

ui_dir = 'app/src/main/java/com/example/ui'

for root, _, files in os.walk(ui_dir):
    for file in files:
        if file.endswith('.kt') and file not in ['SmartText.kt', 'Type.kt', 'FontUtils.kt']:
            path = os.path.join(root, file)
            with open(path, 'r') as f:
                content = f.read()
            
            if 'Text(' in content or 'Text (' in content:
                # Add import if missing
                if 'import com.example.ui.components.SmartText' not in content:
                    content = re.sub(r'^(package [^\n]+)', r'\1\n\nimport com.example.ui.components.SmartText', content, count=1)
                
                # Replace Text( with SmartText( and add // FIXED
                # Regex to match Text( and append // FIXED at end of line
                lines = content.split('\n')
                new_lines = []
                for line in lines:
                    if re.search(r'\bText\s*\(', line):
                        line = re.sub(r'\bText\s*\(', r'SmartText(', line)
                        if '// FIXED' not in line:
                            line = line + ' // FIXED'
                    new_lines.append(line)
                
                with open(path, 'w') as f:
                    f.write('\n'.join(new_lines))
                
                print(f"Patched {path}")
