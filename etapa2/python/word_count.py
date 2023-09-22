import os
import sys
import threading

def wc(content):
    return len(content.split())    

def wc_file(filename):
    try:
        with open(filename, 'r', encoding='latin-1') as f:
            file_content = f.read()
        return wc(file_content)
    except FileNotFoundError:
        return 0
def list_files(filepath, resultado):
    count = 0
    for file in os.listdir(filepath):
        file = os.path.join(filepath, file)
        count+= wc_file(file)
    resultado.append(count)

def wc_dir(dir_path):
    count = 0
    threads = []
    resultado = []
    for filename in os.listdir(dir_path):
        filepath = os.path.join(dir_path, filename)
        if os.path.isdir(filepath):
            folder_thread = threading.Thread(target=list_files, args=(filepath,resultado)) 
            threads.append(folder_thread)
            folder_thread.start()
    for i in range(len(threads)):
        threads[i].join()
        count+= resultado[i]
    return count
def main():
    if len(sys.argv) != 2:
        print("Usage: python", sys.argv[0], "root_directory_path")
        return
    root_path = os.path.abspath(sys.argv[1])
    count = wc_dir(root_path)
    print(count)

if __name__ == "__main__":
    main()
