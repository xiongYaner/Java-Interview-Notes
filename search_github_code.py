#!/usr/bin/env python3
import requests
import re
import time

# 搜索GitHub上的飞书API代码示例
search_terms = [
    "feishu docx api update",
    "飞书API 文档更新代码",
    "Feishu API document update example",
    "飞书docx API 代码示例"
]

for term in search_terms:
    print(f"\n=== 搜索: {term} ===")
    
    github_search = f"https://github.com/search?type=Code&q={requests.utils.quote(term)}"
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36",
        "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"
    }
    
    try:
        response = requests.get(github_search, headers=headers, timeout=15)
        
        if response.status_code == 200:
            print("搜索成功")
            
            # 查找代码片段
            code_pattern = r'<a href="/([^"]+)/blob/[^"]+">([^<]+)</a>'
            matches = re.findall(code_pattern, response.text)
            
            for repo, file in matches[:10]:
                if "feishu" in repo.lower() or "lark" in repo.lower():
                    print(f"  仓库: {repo}")
                    print(f"  文件: {file}")
                    
                    # 获取代码内容
                    if file.endswith(".py") or file.endswith(".js") or file.endswith(".java"):
                        code_url = f"https://github.com/{repo}/blob/master/{file}"
                        print(f"    代码链接: {code_url}")
                        
    except Exception as e:
        print(f"搜索错误: {e}")
        
    time.sleep(1)

print("\n=== 搜索完成 ===")