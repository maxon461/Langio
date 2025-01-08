# PLIKI MOZNA RECZNIE WSZUKACJ JESLI NIE MOZNA ZNALEZC PROGRAMEM POD STRONA:https://www.spanishdict.com/pronunciation/árbol (dla árbol)



import os
import requests

def replace_spanish_characters(word):
    replacements = {
        'á': 'a-',
        'é': 'e-',
        'í': 'i-',
        'ó': 'o-',
        'ú': 'u-',
        'ñ': 'n-'
    }
    for original, replacement in replacements.items():
        word = word.replace(original, replacement)
    return word

def generate_video_url(article, word):
    base_url = "https://sdvids.sdcdns.com/translations/es/_mp4/"
    word_encoded = f"{article}%20{replace_spanish_characters(word)}.mp4"
    return f"{base_url}{word_encoded}"

def download_video(video_url, output_dir, filename):
    response = requests.get(video_url, stream=True)
    if response.status_code == 200:
        filepath = os.path.join(output_dir, filename)
        with open(filepath, 'wb') as f:
            for chunk in response.iter_content(chunk_size=1024):
                if chunk:
                    f.write(chunk)
        print(f"Pobrano: {filepath}")
    else:
        print(f"Nie udało się pobrać: {video_url} (Status: {response.status_code})")

if __name__ == "__main__":
    input_file = './finalOutput.csv'   # Nazwa pliku wejściowego
    output_dir = "videos" 

    if not os.path.exists(output_dir):
        os.makedirs(output_dir)

    with open(input_file, 'r', encoding='utf-8') as f:
        lines = f.readlines()

    for line in lines[91:]:
        fields = line.split('|')
        article_word = fields[3].strip()
        article, word = article_word.split(' ', 1)
        english_word = fields[2].strip() 

        video_url = generate_video_url(article, word)

        filename = f"{english_word}_video.mp4"

        download_video(video_url, output_dir, filename)

    print("Proces zakończony.")
