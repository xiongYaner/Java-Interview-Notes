#!/usr/bin/env python3
import os
import requests
import json

# 获取访问token
app_id = os.environ.get("FEISHU_APP_ID", "cli_a9f6c35c42b91cbb")
app_secret = os.environ.get("FEISHU_APP_SECRET", "oJvQPb6wOTp2bBwzdcxKKeQNmm8G1jM0")

response = requests.post(
    "https://open.feishu.cn/open-apis/auth/v3/tenant_access_token/internal",
    headers={"Content-Type": "application/json"},
    json={"app_id": app_id, "app_secret": app_secret}
)
token = response.json()["tenant_access_token"]

# 读取文档内容
with open("java-interview-materials.md", "r", encoding="utf-8") as f:
    content = f.read()

# 获取文档token
doc_url = "https://my.feishu.cn/wiki/SoXNwVDfcizEn2kjv76cNksqnKd?from=from_copylink"
doc_token = doc_url.split("/wiki/")[1].split("?")[0]

# 尝试通过API更新文档
try:
    # 尝试使用docx API
    update_response = requests.put(
        f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_token}/raw_content",
        headers={
            "Authorization": f"Bearer {token}",
            "Content-Type": "application/json"
        },
        json={"content": content}
    )
    
    if update_response.status_code == 200 and update_response.json().get("code") == 0:
        print("✅ 飞书文档更新成功")
    else:
        raise Exception(f"API调用失败: {update_response.text}")
        
except Exception as e:
    print("❌ 自动更新失败")
    print(f"错误信息: {e}")
    print("\n建议您手动更新：")
    print("1. 打开文档链接")
    print("2. 进入编辑模式")
    print("3. 清空现有内容")
    print("4. 复制粘贴 java-interview-materials.md 文件中的内容")