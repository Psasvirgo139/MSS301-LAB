import zipfile
import xml.etree.ElementTree as ET
import os

def parse_docx_to_md(docx_path, md_path):
    with zipfile.ZipFile(docx_path) as z:
        xml_content = z.read('word/document.xml')
    
    tree = ET.fromstring(xml_content)
    ns = {
        'w': 'http://schemas.openxmlformats.org/wordprocessingml/2006/main'
    }
    
    body = tree.find('w:body', ns)
    if body is None:
        print(f"No body found in {docx_path}")
        return

    output = []
    
    for child in body:
        tag = child.tag.split('}')[-1]
        if tag == 'p':
            # Extract paragraph text
            texts = [t.text for t in child.iterfind('.//w:t', ns) if t.text]
            p_text = ''.join(texts).strip()
            if p_text:
                # Check style for headings
                pStyle = child.find('.//w:pStyle', ns)
                style_val = pStyle.attrib.get('{http://schemas.openxmlformats.org/wordprocessingml/2006/main}val', '') if pStyle is not None else ''
                if 'Heading1' in style_val or style_val == '1':
                    output.append(f"# {p_text}\n")
                elif 'Heading2' in style_val or style_val == '2':
                    output.append(f"## {p_text}\n")
                elif 'Heading3' in style_val or style_val == '3':
                    output.append(f"### {p_text}\n")
                else:
                    output.append(f"{p_text}\n")
        elif tag == 'tbl':
            # Extract table
            rows = []
            for tr in child.iterfind('./w:tr', ns):
                cells = []
                for tc in tr.iterfind('./w:tc', ns):
                    tc_texts = [t.text for t in tc.iterfind('.//w:t', ns) if t.text]
                    cell_text = ' '.join(''.join(tc_texts).split())
                    cells.append(cell_text)
                if cells:
                    rows.append(cells)
            
            if rows:
                max_cols = max(len(r) for r in rows)
                # Normalize row length
                norm_rows = [r + [''] * (max_cols - len(r)) for r in rows]
                
                # Header row
                header = norm_rows[0]
                output.append("| " + " | ".join(header) + " |")
                output.append("| " + " | ".join(['---'] * max_cols) + " |")
                for r in norm_rows[1:]:
                    output.append("| " + " | ".join(r) + " |")
                output.append("\n")

    with open(md_path, 'w', encoding='utf-8') as f:
        f.write('\n'.join(output))
    print(f"Successfully converted {docx_path} -> {md_path}")

if __name__ == '__main__':
    parse_docx_to_md('DeThiSample_New.docx', 'DeThiSample_New.md')
    parse_docx_to_md('HuongDanSample_New.docx', 'HuongDanSample_New.md')
