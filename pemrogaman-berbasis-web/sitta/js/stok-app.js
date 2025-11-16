const { createApp, ref, onMounted } = Vue;
import { dataBahanAjar as _dataBahanAjar } from './data.js'

const bahanAjar = ref([]);
const form = ref({
	kodeLokasi: '',
	kodeBarang: '',
	namaBarang: '',
	jenisBarang: '',
	edisi: '',
	stok: '',
	cover: '',
});
const modalForm = ref(false);

function syncData() {
	const cached = localStorage.getItem('dataBahanAjar')
	if (!cached) {
		localStorage.setItem('dataBahanAjar', JSON.stringify(_dataBahanAjar))
	}

	if (!bahanAjar.value.length) {
		const dataBahanAjar = [...(cached ? JSON.parse(cached) : [])]
		bahanAjar.value = dataBahanAjar;
	}
}

function handleModalForm(bool, e) {
	if (!bool && e.target.tagName !== 'DIALOG') return;
	modalForm.value = bool;
}

async function handleChangeFile(e) {
	if (!e.target.files) return;
	const raw = e.target.files[0];

	if (raw.size >= .3 * (1024 * 1024)) {
		form.value.validation = "Gambar tidak boleh melebihi 300Kb";
		return
	} else form.value.validation = ""

	const r = await new Promise(r => {
		const loader = new FileReader()

		loader.onerror = () => alert('Gambar tidak valid')
		loader.onload = function (event) {

			if (!/data:image/.test(loader.result)) {
				alert('Gambar tidak valid')
				return null
			}
			return r(loader.result)
		}
		loader.readAsDataURL(raw)
	})

	if (!r) return;

	form.value.cover = r
}

function handleRemove(v) {
	const ok = window.confirm(`Anda yakin untuk menghapus ${v.namaBarang}`)

	if (!ok) return;

	const result = bahanAjar.value.filter((r) => r.kodeBarang !== v.kodeBarang);

	localStorage.setItem('dataBahanAjar', JSON.stringify(result))
	bahanAjar.value = result;

	syncData();
}

function handleSaveData() {
	const payload = [...bahanAjar.value, { ...form.value }];
	localStorage.setItem('dataBahanAjar', JSON.stringify(payload))
	bahanAjar.value = payload;
	syncData();
	modalForm.value = false;
}

const app = createApp({
	setup() {

		onMounted(() => {
			syncData();
		});

		return {
			bahanAjar,
			modalForm,
			form,
			handleModalForm,
			handleSaveData,
			handleChangeFile,
			handleRemove
		}
	}
})

app.mount('#app');