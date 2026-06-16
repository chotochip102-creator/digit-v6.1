import sys
data = open("app/src/main/java/com/example/ui/whiteboard/WhiteboardScreen.kt").read()
print(f"Open Parentheses: {data.count('(')}")
print(f"Close Parentheses: {data.count(')')}")
print(f"Open Braces: {data.count('{')}")
print(f"Close Braces: {data.count('}')}")
