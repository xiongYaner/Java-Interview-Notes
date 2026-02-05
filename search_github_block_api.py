#!/usr/bin/env python3
import requests
import re
import time

# 搜索GitHub上的飞书API块更新示例
search_terms = [
    "飞书API 文本块更新",
    "Feishu API update text block code example",
    "飞书API block_type 2 文本块",
    "飞书API docx blocks 代码示例"
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
                    if file.endswith(".py") or file.endswith(".js"):
                        code_url = f"https://github.com/{repo}/blob/master/{file}"
                        print(f"    代码链接: {code_url}")
                        
                        try:
                            code_response = requests.get(code_url, headers=headers, timeout=10)
                            
                            if "text_run" in code_response.text or "block_type" in code_response.text:
                                print("    包含文本块相关代码")
                                
                                # 提取相关代码片段
                                content = code_response.text
                                if "update" in term.lower():
                                    update_pattern = r'update.*block|block.*update'
                                else:
                                    update_pattern = r'text.*block|block.*text'
                                    
                                matches = re.findall(update_pattern, content, re.IGNORECASE)
                                if matches:
                                    print(f"    找到 {len(matches)} 处相关代码")
                                    
                                # 提取JSON结构
                                json_pattern = r'{[^\}]+"text"[^\}]+}'
                                json_matches = re.findall(json_pattern, content)
                                for j_match in json_matches[:1]:
                                    print(f"    JSON结构: {j_match}")
                                    
                        except Exception as e:
                            print(f"    访问代码失败: {e}")
                        
    except Exception as e:
        print(f"搜索错误: {e}")
        
    time.sleep(1)

print("\n=== 搜索完成 ===")