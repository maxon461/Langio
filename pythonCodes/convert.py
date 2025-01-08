# Funkcja do przetwarzania pliku
def process_file(input_file, output_file):
    with open(input_file, 'r', encoding='utf-8') as infile, open(output_file, 'w', encoding='utf-8') as outfile:
        for line in infile:
            parts = line.strip().split('|')
            
            # Sprawdzenie, czy linia ma odpowiednią liczbę elementów
            if len(parts) >= 10:
                # Połączenie elementów 5-10 w odpowiedni format
                parts[4] = f"[{';'.join(parts[4:10])}]"
                # Usunięcie oryginalnych elementów 6-10
                parts = parts[:5] + parts[10:]
                
            # Zapisz zmodyfikowaną linię
            outfile.write('|'.join(parts) + '\n')

# Przykład użycia
input_file = 'Zeszyt1.csv'  # Nazwa pliku wejściowego
output_file = 'finalOutput.csv'  # Nazwa pliku wyjściowego

process_file(input_file, output_file)
