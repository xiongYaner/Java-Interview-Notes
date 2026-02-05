#!/usr/bin/env python3
"""
尝试获取飞书API官方文档
"""

import requests
import json

# 搜索飞书API文档
search_terms = [
    "飞书API文档 docx 接口",
    "飞书API docx v1 文档操作",
    "飞书API wiki文档更新",
    "飞书API raw_content 接口",
    "飞书API 文档内容更新",
    "Feishu API docx document update"
]

for term in search_terms:
    try:
        print(f"=== 搜索: {term} ===")
        
        # 使用Bing搜索
        bing_url = f"https://www.bing.com/search?q={requests.utils.quote(term)}"
        headers = {
            "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36"
        }
        
        response = requests.get(bing_url, headers=headers, timeout=10)
        
        if response.status_code == 200:
            print("搜索成功")
            
            # 查找飞书开放平台文档链接
            open_feishu_links = []
            if "open.feishu.cn/document" in response.text:
                import re
                doc_pattern = r'https://open\.feishu\.cn/document/[^"]+'
                matches = re.findall(doc_pattern, response.text)
                open_feishu_links = list(set(matches))
                
                print(f"找到 {len(open_feishu_links)} 个飞书API文档链接")
                for link in open_feishu_links[:3]:
                    print(f"- {link}")
                    # 检查是否包含docx相关内容
                    try:
                        doc_response = requests.get(link, headers=headers, timeout=10)
                        if "docx" in doc_response.text.lower():
                            print(f"  ✅ 包含docx接口")
                    except Exception as e:
                        print(f"  ❌ 访问失败: {e}")
            
            # 查找GitHub上的API示例
            github_links = []
            if "github.com" in response.text:
                import re
                github_pattern = r'https://github\.com/[^"]+feishu[^"]+'
                matches = re.findall(github_pattern, response.text)
                github_links = list(set(matches))
                
                print(f"找到 {len(github_links)} 个GitHub示例链接")
                for link in github_links[:2]:
                    print(f"- {link}")
            
        else:
            print(f"搜索失败: {response.status_code}")
            
        print()
        
    except Exception as e:
        print(f"搜索错误: {e}")
        print()

print("=== 搜索完成 ===")