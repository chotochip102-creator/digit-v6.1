import sys

data = open("app/src/main/java/com/example/ui/whiteboard/WhiteboardScreen.kt").read()

stack = []
for i, c in enumerate(data):
    if c in '({[':
        stack.append((c, i))
    elif c in ')}]':
        if not stack:
            print(f"Extra closing {c} at {i}")
        else:
            top, pos = stack.pop()
            if (top == '(' and c != ')') or (top == '{' and c != '}') or (top == '[' and c != ']'):
                print(f"Mismatched closing {c} for {top} at {pos}")

for top, pos in stack:
    # get line number
    line_no = data[:pos].count('\n') + 1
    print(f"Unclosed {top} at line {line_no}")
