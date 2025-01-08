import csv
import os

listUsed = []

def is_valid_row(row, video_folder):
    id1, id2, x, y, options, g, h = row
    options_list = options.strip('[]').split(';')

    if x in listUsed:
        return False
    else:
        if len(x) > 12 or len(y) > 12:
            return False

        for option in options_list:
            if len(option.strip()) > 12:
                return False

        if not is_video_present(x, video_folder):
            print(f"Brak pliku wideo: {x}_video.mp4")
            return False
    listUsed.append(x)
    return True

def is_video_present(x, video_folder):
    video_filename = f"{x.strip()}_video.mp4"
    print(os.path.join(video_folder, video_filename))
    return os.path.isfile(os.path.join(video_folder, video_filename))

input_file = 'finalOutput.csv' 
output_file = 'output_new.csv' 
video_folder = 'videos'

with open(input_file, newline='', encoding='utf-8') as infile:
    reader = csv.reader(infile, delimiter='|')
    with open(output_file, mode='w', newline='', encoding='utf-8') as outfile:
        writer = csv.writer(outfile, delimiter='|')

        for row in reader:
            # Przetwarzaj wiersze
            if is_valid_row(row, video_folder):
                writer.writerow(row)
            else:
                print(f"ODRZUCONO {row[:3]}")

print("Przetwarzanie zakończone, wiersze zapisane do output.csv")
