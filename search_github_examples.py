#!/usr/bin/env python3
import requests
import re
import json

keywords = [
    "feishu docx api raw_content",
    "feishu doc update api",
    "飞书API 文档更新",
    "feishu api document edit"
]

for keyword in keywords:
    print(f"\n=== 搜索: {keyword} ===")
    
    # 使用GitHub搜索
    github_url = f"https://github.com/search?type=Code&q={requests.utils.quote(keyword)}"
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36",
        "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"
    }
    
    try:
        response = requests.get(github_url, headers=headers, timeout=15)
        
        if response.status_code == 200:
            print("搜索成功")
            
            # 查找仓库和代码片段
            repo_pattern = r'href="/([^"]+?/[^"]+?)"'
            repos = re.findall(repo_pattern, response.text)
            
            for repo in repos[:5]:
                if "feishu" in repo.lower() or "lark" in repo.lower() or "dingtalk" in repo.lower():
                    print(f"- 仓库: {repo}")
                    
                    # 查找代码片段
                    code_url = f"https://github.com/{repo}/search?type=Code&q={requests.utils.quote('raw_content')}"
                    try:
                        code_response = requests.get(code_url, headers=headers, timeout=10)
                        
                        if "raw_content" in code_response.text:
                            print(f"  ✅ 包含raw_content接口")
                            
                            # 查找API调用示例
                            api_pattern = r'https://open\.feishu\.cn/open-apis/[^"\s]+'
                            api_matches = re.findall(api_pattern, code_response.text)
                            for api_url in api_matches[:5]:
                                print(f"    {api_url}")
                        
                    except Exception as e:
                        print(f"  ❌ 访问失败: {e}")
                        
        else:
            print(f"搜索失败: {response.status_code}")
            
    except Exception as e:
        print(f"搜索错误: {e}")

print("\n=== 搜索完成 ===")