import csv
from gtts import gTTS
import os

# Funkcja do generowania pliku audio
def generate_audio(text, lang, output_filename):
    tts = gTTS(text=text, lang=lang)
    tts.save(output_filename)
    print(f"Plik audio zapisany: {output_filename}")

# Funkcja do wczytania CSV i generowania plików audio dla wybranego słowa
def generate_audio_for_word(csv_file):
    # Otwórz plik CSV
    with open(csv_file, mode='r', encoding='utf-8') as file:
        csv_reader = csv.reader(file, delimiter='|')
        
        for row in csv_reader:
            word = row[2].strip()  # Słowo w języku angielskim (kolumna 3)
            spanish_word = row[3].strip()  # Słowo po hiszpańsku (kolumna 4)
            
            # Sprawdzamy, czy to jest nasze słowo
            # Generowanie audio dla wersji angielskiej
            generate_audio(word, 'en', f"./audio/en/{word}_en.mp3")
            
            # Generowanie audio dla wersji hiszpańskiej
            generate_audio(spanish_word, 'es', f"./audio/es/{word}_es.mp3")
            



# Wskazanie ścieżki do pliku CSV
csv_file_path = './finalOutput.csv'  

# csv_file_path = 'D:/ProgrammingProjects/Kotlin/LANGIO_repo/Langio/app/src/main/res/raw/words.csv'  # Zmień na odpowiednią ścieżkę do pliku CSV

# Uruchomienie funkcji
generate_audio_for_word(csv_file_path)
