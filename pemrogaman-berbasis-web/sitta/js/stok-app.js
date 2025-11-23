const colorStatus = {
	'Aman': '#00FF00',
	'Menipis': '#FFFF00',
	'Kosong': 'FF0000'
}

const bahanAjar = Vue.ref([]);

const mode = Vue.ref('normal');
const filter = Vue.ref('');
const filterBy = Vue.ref('');
const sort = Vue.ref('asc');

function syncData() {
	const version = localStorage.getItem('version');
	if (version !== releaseVersion) {
		localStorage.setItem('version', releaseVersion);
		localStorage.removeItem('dataBahanAjar');
	};
	const cached = localStorage.getItem('dataBahanAjar')
	if (!cached) {
		localStorage.setItem('dataBahanAjar', JSON.stringify(dataBahanAjar))
	}

	if (!bahanAjar.value.length) {
		const dataBahanAjar = [...(cached ? JSON.parse(cached) : [])]
		bahanAjar.value = dataBahanAjar;
	}
}

function handleAppendData() {
	bahanAjar.value = [{}, ...bahanAjar.value]
}

function handleRemove(v) {
	const ok = window.confirm(`Anda yakin untuk menghapus ${v.namaBarang}`)

	if (!ok) return;

	const result = bahanAjar.value.filter((r) => r.kode !== v.kode);

	localStorage.setItem('dataBahanAjar', JSON.stringify(result))
	bahanAjar.value = result;

	syncData();
}

let timeoutId;

const app = Vue.createApp({
	setup() {

		Vue.onMounted(() => {
			syncData();
		});

		const filteredBahanAjar = Vue.computed(() => {
			return bahanAjar.value.flatMap((r, i) => {

				let status = 'Kosong';
				if (r.qty >= +r.safety) status = 'Aman';
				else if (r.qty > 0) status = 'Menipis';

				if (filter.value) {
					const exist = r[filterBy.value]?.toLowerCase().includes(filter.value.toLowerCase());
					if (!exist) return [];
				}
				return { ...r, safety: +r.safety || 0, status, i };
			}).sort((a, b) => {
				const aa = ['judul', 'qty', 'harga'].map(k => a[k]).join(',');
				const bb = ['judul', 'qty', 'harga'].map(k => b[k]).join(',');
				if (sort.value === 'asc') return aa.localeCompare(bb);
				return bb.localeCompare(bb);
			})
		})

		const filterOptions = Vue.computed(() => {
			return {
				'upbjj': upbjjList,
				'kategori': kategoriList
			}[filterBy.value] ?? []
		})

		const handleResetFilter = () => {
			filter.value = '';
			filterBy.value = '';
		}

		Vue.watch(bahanAjar, (newVal, prev) => {
			if (timeoutId) clearTimeout(timeoutId);
			if (newVal.some(r => r.error) || !prev.length) return;

			timeoutId = setTimeout(() => {
				const payload = [...newVal];
				localStorage.setItem('dataBahanAjar', JSON.stringify(payload))
			}, 1000);

		}, { deep: true });

		return {
			mode,
			bahanAjar,
			colorStatus,
			filteredBahanAjar,
			filterOptions,
			filterBy,
			filter,
			sort,
			upbjjList,
			kategoriList,
			isNormalMode: Vue.computed(()=> mode.value === 'normal'),
			isInsertMode: Vue.computed(()=> mode.value === 'insert'),
			handleAppendData,
			handleRemove,
			handleResetFilter
		}
	}
})

app.mount('#app');