// Soal 1. Buatlah Pola Segitiga dari NIM, Ambil digit terakhir NIM Anda sebagai tinggi segitiga. Contoh: NIM 230411013  (digit terakhir = 3,  tinggi segitiga = 3). Buat program TypeScript untuk mencetak segitiga angka dengan pola berikut:
//
// 1
//
// 1 2
//
// 1 2 3
//
const NIM = '051700434';

function segitiga(nim: string) {
  const digitTerakhir = Number.parseInt(nim.slice(-1));
  for (let i = 1; i <= digitTerakhir; i++) {
    let hasil = '';
    for (let j = 1; j <= i; j++) {
      hasil += ` ${j}`;
    }
    console.log(hasil);
  }
}

segitiga(NIM);

//
// Jika tinggi segitiga = 5, maka baris terakhir menampilkan angka 1 sampai 5, dan seterusnya.
//
//  
//
// Soal 2. Buatlah Deret Aritmatika dengan NIM. Ambil 2 digit terakhir NIM, jadikan angka awal deret. Ambil digit ke-3 dari belakang, jadikan beda (step). Buat program TypeScript untuk mencetak 10 angka pertama dari deret aritmatika tersebut.
//
// Contoh: NIM 230411013, 2 digit terakhir = 13,  berarti start dari 13.  digit ke-3 dari belakang + 1 (0 + 1), sehingg beda nya = 1 step.
//
// Output:13, 14, 15, 16, 17, 18, 19, 20, 21, 22

function deret(nim: string) {
  const duaDigitTerakhir = Number.parseInt(nim.slice(-2))
  const digitKetigaTerakhir = Number.parseInt(nim.slice(-3, -2))
  const step = digitKetigaTerakhir + 1;
  let hasil = '';
  for (let i = 1; i <= 10; i++) {
    hasil += `${duaDigitTerakhir + (step * i)}, `
  }
  console.log(hasil);
}

deret(NIM);

//
//  
//
// Soal 3. Buatlah Bilangan Prima dari NIM. Ambil 2 digit terakhir NIM dan tambahkan 10, jadikan batas akhir pencarian bilangan prima. Buat program TypeScript untuk menampilkan semua bilangan prima dari 1 sampai batas tersebut.
//
// Contoh: NIM 230411013, 2 digit terakhir  yaitu 13 ditambah 10, sehingga menjadi 23 (batas akhir)
//
// Output: 2, 3, 5, 7, 11, 13, 17, 19, 23
//
//


function adalahPrima(digit: number, pembagi = 2) {
  if (digit < 2) return false
  if (pembagi > Math.sqrt(digit)) return true;
  if ((digit % pembagi) === 0) return false;
  return adalahPrima(digit, pembagi + 1)
}

function deretPrima(batas: number, digit = 2, hasil: number[] = []) {
  if (hasil.length >= batas) return hasil;
  if (adalahPrima(digit)) hasil.push(digit);
  return deretPrima(batas, digit + 1, hasil)
}

function deretPrimaDenganBatas(nim: string) {
  const duaDigitTerakhir = Number.parseInt(nim.slice(-2))
  console.log(deretPrima(duaDigitTerakhir).join(','));
}

deretPrimaDenganBatas(NIM);
