import os
import sys
import multiprocessing

def wc(content):
    return len(content.split())    

def wc_file(filename):
    try:
        with open(filename, 'r', encoding='latin-1') as f:
            file_content = f.read()
        return wc(file_content)
    except FileNotFoundError:
        print("erro")
        return 0
def list_files(filepath, count):
    count_temp = 0
    for file in os.listdir(filepath):
        file = os.path.join(filepath, file)
        count_temp += wc_file(file)
    count.value += count_temp

def wc_dir(dir_path):
    count = multiprocessing.Value('i', 0)
    process = []
    for filename in os.listdir(dir_path):
        filepath = os.path.join(dir_path, filename)
        if os.path.isdir(filepath):
            folder_process = multiprocessing.Process(target=list_files, args=(filepath,count)) 
            process.append(folder_process)
            folder_process.start()
    for folder_process in process:
        folder_process.join()  
    return count
def main():
    if len(sys.argv) != 2:
        print("Usage: python", sys.argv[0], "root_directory_path")
        return
    root_path = os.path.abspath(sys.argv[1])
    count = wc_dir(root_path)
    print(count.value)

if __name__ == "__main__":
    main()
