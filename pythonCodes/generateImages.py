# from icrawler.builtin import GoogleImageCrawler
# import os
# import shutil

# def download_images(query, output_dir):
#     # Tworzenie katalogu wyjściowego, jeśli nie istnieje
#     if not os.path.exists(output_dir):
#         os.makedirs(output_dir)

#     # Tymczasowy folder
#     temp_dir = os.path.join(output_dir, "temp")
#     if not os.path.exists(temp_dir):
#         os.makedirs(temp_dir)

#     # Konfiguracja crawlera z tymczasowym folderem
#     crawler = GoogleImageCrawler(storage={"root_dir": temp_dir})
#     crawler.crawl(keyword=query, max_num=1)

#     # Przenoszenie i zmiana nazwy pliku
#     for filename in os.listdir(temp_dir):
#         old_path = os.path.join(temp_dir, filename)
#         new_path = os.path.join(output_dir, f"{query}.jpg")
#         os.rename(old_path, new_path)

#     # Usuwanie tymczasowego folderu
#     shutil.rmtree(temp_dir)

# # Pobieranie obrazków
# words = ["imbir", "jablko", "kot", "pies"]
# for word in words:
#     download_images(word, output_dir="./images")



import csv
from icrawler.builtin import GoogleImageCrawler
from PIL import Image
import os

def download_images(query, output_dir):
    temp_dir = os.path.join(output_dir, "temp")
    os.makedirs(temp_dir, exist_ok=True)
    
    crawler = GoogleImageCrawler(storage={"root_dir": temp_dir})
    crawler.crawl(keyword=query, max_num=1)

    # Znajdź pobrany plik w folderze tymczasowym
    for file in os.listdir(temp_dir):
        if file.lower().endswith((".jpg", ".jpeg", ".png", ".bmp")):  # Obsługiwane rozszerzenia
            file_path = os.path.join(temp_dir, file)
            
            # Otwórz obraz i zapisz jako JPG z odpowiednią nazwą
            output_path = os.path.join(output_dir, f"{query}_img.jpg")
            with Image.open(file_path) as img:
                img = img.convert("RGB")  # Konwersja na format RGB
                img.save(output_path, "JPEG")
            
            # Usuń oryginalny plik tymczasowy
            os.remove(file_path)
            break

    # Usuń folder tymczasowy
    os.rmdir(temp_dir)

def read_words_from_csv(csv_path):
    words = []
    with open(csv_path, mode="r", encoding="utf-8") as file:
        reader = csv.reader(file, delimiter="|")
        for row in reader:
            if len(row) >= 3:  # Angielskie słowo jest w trzeciej kolumnie
                words.append(row[2].strip().split(' ')[-1].strip())
    return words

# Główna część programu
csv_file = './finalOutput.csv'  
output_directory = './images'  

os.makedirs(output_directory, exist_ok=True)  # Utwórz folder wyjściowy, jeśli nie istnieje

# Wczytaj słówka i pobierz obrazy
words = read_words_from_csv(csv_file)
for word in words:
    print(f"Pobieranie obrazu dla: {word}")
    download_images(word, output_dir=output_directory)

print("Pobieranie obrazów zakończone!")
