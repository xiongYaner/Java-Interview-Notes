#!/usr/bin/env python3
import requests
import json

app_id = "cli_a9f6c35c42b91cbb"
app_secret = "oJvQPb6wOTp2bBwzdcxKKeQNmm8G1jM0"

# 获取token
token_response = requests.post(
    "https://open.feishu.cn/open-apis/auth/v3/tenant_access_token/internal",
    headers={"Content-Type": "application/json"},
    json={"app_id": app_id, "app_secret": app_secret}
)
token = token_response.json()["tenant_access_token"]

# 测试API响应
doc_url = "https://my.feishu.cn/wiki/SoXNwVDfcizEn2kjv76cNksqnKd?from=from_copylink"
doc_token = doc_url.split("/wiki/")[1].split("?")[0]

test_endpoints = [
    f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_token}",
    f"https://open.feishu.cn/open-apis/docx/v1/spaces/{doc_token}",
    f"https://open.feishu.cn/open-apis/wiki/v2/spaces/key/{doc_token}",
    f"https://open.feishu.cn/open-apis/wiki/v1/wiki/get?wiki_token={doc_token}"
]

for endpoint in test_endpoints:
    print(f"\n=== 测试: {endpoint} ===")
    
    headers = {
        "Authorization": f"Bearer {token}",
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36"
    }
    
    try:
        response = requests.get(endpoint, headers=headers, timeout=10)
        
        print(f"状态码: {response.status_code}")
        print(f"响应: {response.text}")
        
        print(f"\n响应头:")
        for key, value in response.headers.items():
            print(f"  {key}: {value}")
            
        if response.status_code == 200:
            try:
                result = response.json()
                print(f"\nJSON响应:")
                print(json.dumps(result, indent=2, ensure_ascii=False))
            except Exception as e:
                print(f"解析JSON失败: {e}")
                
    except Exception as e:
        print(f"请求错误: {e}")

print("\n=== 测试完成 ===")