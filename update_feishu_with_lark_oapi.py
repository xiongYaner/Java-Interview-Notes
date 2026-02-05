#!/usr/bin/env python3
"""
使用飞书官方API更新文档内容
"""

import os
import json
from lark_oapi import Client
from lark_oapi.core.model.config import Config
from lark_oapi.api.docx.v1 import Document, RawContentDocumentRequest
from lark_oapi.api.docx.v1 import PatchDocumentBlockRequest, UpdateBlockRequest
from lark_oapi.api.docx.v1 import UpdateTextRequest, TextElement, TextRun
from lark_oapi.api.docx.v1 import ListDocumentBlockRequest

# 配置应用信息
app_id = "cli_a9f6c35c42b91cbb"
app_secret = "oJvQPb6wOTp2bBwzdcxKKeQNmm8G1jM0"

# 配置客户端
config = Config.builder() \
    .app_id(app_id) \
    .app_secret(app_secret) \
    .build()
client = Client(config)

# 文档信息
doc_url = "https://my.feishu.cn/wiki/SoXNwVDfcizEn2kjv76cNksqnKd?from=from_copylink"
doc_id = doc_url.split("/wiki/")[1].split("?")[0]
print(f"文档ID: {doc_id}")

# 读取要更新的内容
with open("java-interview-materials.md", "r", encoding="utf-8") as f:
    content_str = f.read()
print(f"要更新的内容长度: {len(content_str)}")

# 初始化API
document_api = Document(config)

# 步骤1: 获取文档的原始内容
print("\n=== 步骤1: 获取文档的原始内容 ===")
get_raw_content_request = RawContentDocumentRequest.builder() \
    .document_id(doc_id) \
    .build()
get_raw_content_response = document_api.raw_content(get_raw_content_request)
if get_raw_content_response.code == 0:
    print(f"原始内容长度: {len(get_raw_content_response.data.content)}")
else:
    print(f"获取原始内容失败: code={get_raw_content_response.code}, msg={get_raw_content_response.msg}")
    exit(1)

# 步骤2: 获取文档的块信息
print("\n=== 步骤2: 获取文档的块信息 ===")
list_block_request = ListDocumentBlockRequest.builder() \
    .document_id(doc_id) \
    .build()
list_block_response = document_api.list_document_block(list_block_request)
if list_block_response.code == 0:
    print(f"文档包含 {len(list_block_response.data.items)} 个块")
    text_block = None
    for block in list_block_response.data.items:
        if block.block_type == 2:  # 文本块类型
            text_block = block
            print(f"找到文本块: id={block.block_id}")
            break
            
    if not text_block:
        print("未找到文本块，无法更新")
        exit(1)
else:
    print(f"获取块信息失败: code={list_block_response.code}, msg={list_block_response.msg}")
    exit(1)

# 步骤3: 构造文本内容
print("\n=== 步骤3: 构造文本内容 ===")
text_run = TextRun.builder() \
    .content(content_str) \
    .build()

text_element = TextElement.builder() \
    .text_run(text_run) \
    .build()

update_text_request = UpdateTextRequest.builder() \
    .elements([text_element]) \
    .build()

update_block_request = UpdateBlockRequest.builder() \
    .update_text(update_text_request) \
    .build()

patch_document_block_request = PatchDocumentBlockRequest.builder() \
    .document_id(doc_id) \
    .block_id(text_block.block_id) \
    .request_body(update_block_request) \
    .build()

# 步骤4: 更新文本块
print("\n=== 步骤4: 更新文本块 ===")
patch_block_response = document_api.patch_document_block(patch_document_block_request)
if patch_block_response.code == 0:
    print("✅ 文本块更新成功")
    
    # 步骤5: 验证更新
    print("\n=== 步骤5: 验证更新 ===")
    verify_response = document_api.raw_content(get_raw_content_request)
    if verify_response.code == 0:
        print(f"更新后的内容长度: {len(verify_response.data.content)}")
        print("✅ 文档内容已成功更新！")
    else:
        print(f"验证更新失败: code={verify_response.code}, msg={verify_response.msg}")
        
else:
    print(f"更新失败: code={patch_block_response.code}, msg={patch_block_response.msg}")
    print(f"详细信息: {patch_block_response.raw.content.decode('utf-8')}")