import getopt
import os
import shutil
import signal
import sys
import time
import zipfile


def write_channel_to_apk(apk_file, channel_name):
    z = zipfile.ZipFile(apk_file, 'a', zipfile.ZIP_DEFLATED)
    empty_channel_file = f"META-INF/CHANNEL_{channel_name}"
    temp = "temp.apk"
    if not os.path.exists(temp):
        open(temp, 'a').close()
    z.write(temp, empty_channel_file)
    z.close()
    fname, ext = os.path.splitext(apk_file)
    os.system(f"apksigner sign --ks {your.keystore} --ks-key-alias {your key.alias} --ks-pass pass:{your pass} --key-pass "
              f"pass:{yourxxxx} --out {fname}_resign{ext} {apk_file}")
    os.remove(apk_file)


def cp_file(src_file, target_file):
    if os.path.exists(target_file):
        os.remove(target_file)
    shutil.copy(src_file, target_file)


def signal_exit_handler(signal_num, frame):
    sys.exit(signal_num)


if __name__ == '__main__':
    # Ctrl+Z (Mac) 会触发 SIGINT 信号
    # Ctrl+C (Windows) 会触发 SIGINT 信号
    signal.signal(signal.SIGINT, signal_exit_handler)

    try:
        # 第二个参数是短参数，如果没有参数值则不需要写':'(例如 a:b:cd,使用时 -a就必须制定值，-c/-d 则不需要)
        # 第三个参数是长参数，如果没有参数值则不需要写'='(help，lang=,使用时 --help 不用指定值，--lang就必须制定值，如 --lang=CN)
        options, args = getopt.getopt(sys.argv[1:], 'h:', ['help'])
        channel_file = args[0]
        src_apk_file = args[1]
        file = open("channel.txt")
        if not os.path.exists(src_apk_file):
            print("source file " + src_apk_file + " not exists")
            sys.exit(1)

        file_name, file_ext = os.path.splitext(src_apk_file)
        start = time.time()

        for line in file:
            channel = line.strip('\n').strip()
            target_file_name = f"{file_name}_{channel}.apk"
            print(f"复制文件:{target_file_name}")
            cp_file(src_apk_file, target_file_name)
            print(f"往{target_file_name}文件写入渠道:{channel}")
            write_channel_to_apk(target_file_name, channel)

        print("The function run time is : %.03f seconds" % (time.time() - start))

    except getopt.GetoptError as err:
        print(err)
