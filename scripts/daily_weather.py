
#!/usr/bin/env python3
import subprocess
import requests
import json

def get_weather_by_coords(lat, lon, location_name):
    """通过经纬度获取天气信息"""
    try:
        url = f"https://api.open-meteo.com/v1/forecast?latitude={lat}&longitude={lon}&current_weather=true"
        response = requests.get(url, timeout=5)
        response.raise_for_status()
        data = response.json()
        current = data.get("current_weather", {})
        temp = current.get("temperature", "N/A")
        windspeed = current.get("windspeed", "N/A")
        weathercode = current.get("weathercode", "N/A")
        return f"{location_name}: {weathercode_to_desc(weathercode)} {temp}°C 风速 {windspeed} km/h"
    except Exception as e:
        print(f"获取天气失败 {location_name}: {e}")
        return f"{location_name}: 天气获取失败"

def weathercode_to_desc(code):
    """将天气代码转换为描述"""
    weather_codes = {
        0: "晴朗",
        1: "多云",
        2: "多云",
        3: "阴天",
        45: "雾",
        48: "雾",
        51: "小雨",
        53: "小雨",
        55: "小雨",
        61: "中雨",
        63: "中雨",
        65: "大雨",
        71: "小雪",
        73: "小雪",
        75: "大雪",
        77: "雪",
        80: "阵雨",
        81: "阵雨",
        82: "暴雨",
        95: "雷暴",
        96: "雷暴",
        99: "雷暴"
    }
    return weather_codes.get(code, "未知")

def get_weather(location):
    """获取指定地点的天气信息"""
    # 南京江宁的经纬度
    if location == "南京江宁":
        return get_weather_by_coords(31.95, 118.87, "南京江宁")
    # 泰州的经纬度
    elif location == "泰州":
        return get_weather_by_coords(32.44, 119.98, "泰州")
    else:
        return f"{location}: 未支持的地点"

def send_message(content):
    """发送消息给用户"""
    try:
        cmd = f"clawdbot message send --channel feishu --target ou_f62763f9be04a1580329d7b8f77041a4 --message '{content}'"
        result = subprocess.run(cmd, shell=True, capture_output=True, text=True, timeout=10)
        print("消息发送成功")
        print(result.stdout)
        if result.stderr:
            print("错误信息:")
            print(result.stderr)
    except Exception as e:
        print(f"发送消息失败: {e}")

def main():
    # 获取天气
    jiangning_weather = get_weather("南京江宁")
    taizhou_weather = get_weather("泰州")
    
    # 构建消息
    message = f"天气预报：\n{jiangning_weather}\n{taizhou_weather}"
    
    # 发送消息
    send_message(message)

if __name__ == "__main__":
    main()
