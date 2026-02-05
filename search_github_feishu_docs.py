#!/usr/bin/env python3
"""
搜索GitHub上的飞书API文档示例
"""

import requests
import re

# 搜索GitHub上的飞书API文档示例
search_url = "https://github.com/search?type=Code&q=feishu+api+docx+blocks"

headers = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36",
    "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"
}

try:
    print("=== 搜索GitHub上的飞书API文档示例 ===")
    response = requests.get(search_url, headers=headers, timeout=15)
    
    if response.status_code == 200:
        print("✅ 搜索成功")
        
        # 查找仓库和文件
        code_links = re.findall(r'href="/([^"]+)/blob/([^"]+)"', response.text)
        
        # 过滤与飞书API相关的代码
        relevant_links = []
        
        for repo, file in code_links:
            if any(keyword in repo.lower() or keyword in file.lower() for keyword in ["feishu", "lark", "docx", "blocks"]):
                if file.endswith(".py") or file.endswith(".js") or file.endswith(".java"):
                    relevant_links.append(f"https://github.com/{repo}/blob/{file}")
        
        print(f"找到 {len(relevant_links)} 个相关代码示例")
        
        # 访问第一个代码示例
        if relevant_links:
            first_code_url = relevant_links[0]
            print(f"\n=== 访问第一个代码示例: {first_code_url} ===")
            
            # 转换为原始内容链接
            raw_url = first_code_url.replace("/blob/", "/raw/")
            
            code_response = requests.get(raw_url, headers=headers, timeout=10)
            
            if code_response.status_code == 200:
                code_content = code_response.text
                
                # 查找API调用相关代码
                if "docx" in code_content.lower() and ("blocks" in code_content.lower() or "update" in code_content.lower()):
                    print("✅ 找到相关的docx API调用代码")
                    
                    # 提取API接口
                    api_pattern = r'https://open\.feishu\.cn/open-apis/[^"\s]+'
                    api_matches = re.findall(api_pattern, code_content)
                    
                    if api_matches:
                        print("\n找到的API接口:")
                        for api in set(api_matches):
                            if "docx" in api or "blocks" in api:
                                print(f"  - {api}")
                    
                    # 提取JSON结构
                    json_pattern = r'{[^\}]+"text"[^\}]+}'
                    json_matches = re.findall(json_pattern, code_content)
                    
                    if json_matches:
                        print("\n找到的JSON结构:")
                        for j_str in json_matches[:2]:
                            print(f"  - {j_str}")
                            
            else:
                print(f"❌ 代码访问失败: {code_response.status_code}")
                
    else:
        print(f"❌ 搜索失败: {response.status_code}")
        
except Exception as e:
    print(f"❌ 错误: {e}")