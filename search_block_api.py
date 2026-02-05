#!/usr/bin/env python3
import requests
import re
import time

# 搜索飞书API文档中关于块更新的参数
search_terms = [
    "飞书API docx blocks 更新参数",
    "飞书API 文本块更新请求格式",
    "Feishu API update text block JSON structure",
    "飞书API block_type 2 参数格式"
]

for term in search_terms:
    print(f"\n=== 搜索: {term} ===")
    
    search_url = f"https://www.bing.com/search?q={requests.utils.quote(term)}"
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36",
        "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
        "Accept-Language": "zh-CN,zh;q=0.8"
    }
    
    try:
        response = requests.get(search_url, headers=headers, timeout=10)
        
        if response.status_code == 200:
            print("搜索成功")
            
            # 查找飞书开放平台文档链接
            pattern = r'https://open\.feishu\.cn/document/[^"\s]+'
            matches = re.findall(pattern, response.text)
            
            for match in matches:
                if any(keyword in match for keyword in ["docx", "blocks", "update"]):
                    print(f"  找到文档: {match}")
                    
                    # 访问API文档页面
                    try:
                        doc_response = requests.get(match, headers=headers, timeout=10)
                        
                        if doc_response.status_code == 200:
                            # 查找参数格式
                            if "请求体" in doc_response.text or "参数" in doc_response.text:
                                print("    找到请求体/参数说明")
                                
                            # 查找JSON示例
                            json_pattern = r'{[^\}]+"text"[^\}]+}'
                            json_matches = re.findall(json_pattern, doc_response.text)
                            for j_match in json_matches[:2]:
                                print(f"    JSON示例: {j_match}")
                        
                    except Exception as e:
                        print(f"    访问文档失败: {e}")
                        
    except Exception as e:
        print(f"搜索错误: {e}")
        
    time.sleep(1)

print("\n=== 搜索完成 ===")