const app = Vue.createApp({
	mounted() {
		this.dataTracking = dataTracking;
	},
	methods: {
		async tracking() {
			this.loading = true;

			const nomorDO = this.inputNomorDO.toUpperCase().trim();

			const dataTrack = this.dataTracking[nomorDO]

			if (!dataTrack) {
				this.loading = false;
				return window.alert('No. DO / Billing tidak ditemukan')
			}
			await wait(1000);
			this.loading = false;

			this.dataTrack = { ...dataTrack, nomorDO };
		},
		resetForm() {
			this.form = {
				action: 'I',
				noDO: `DO${new Date().getFullYear()}-${(Object.keys(this.tracking).length + 1).toString().padStart(4, '0')}`,
				nim: '',
				nama: '',
				ekspedisi: '',
				paket: '',
				tanggalKirim: '',
				status: '',
				total: '',
				perjalanan: [],
			}
		},
		tambah() {
			this.resetForm()
			this.modalCreate = true
		},
		save() {
			if (Object.values(this.errors).filter(value => value).length > 0) {
				alert('Periksa input form!')
			} else {
				const { action, noDO, ...payload } = this.form
				payload.total = this.paket.find(item => item.kode == payload.paket).harga
				if (action == 'I') {
					const data = this.dataTracking[noDO]
					if (data) {
						alert('Data sudah ada!')
					} else {
						this.dataTracking[noDO] = payload
						alert('Tambah Data Berhasil!')
						this.modalCreate = false;
					}
				} else {
					const data = this.dataTracking[noDO]
					if (!data) {
						alert('Data tidak ada!')
					} else {
						this.dataTracking[noDO] = payload
						this.terlacak = payload
						this.terlacak.noDO = noDO
						alert('Ubah Data Berhasil!')
						this.modalCreate = false;
					}
				}
			}
		}
	},
	data() {
		return {
			inputNomorDO: '',
			loading: false,
			modalCreate: false,
			dataTrack: {
				nama: '',
				nomorDO: '',
				tanggalKirim: '',
				ekspedisi: '',
				paket: '',
				total: '',
				perjalanan: [],
				dataTracking: [],
				status: '',
			},
			form: {
				action: 'I',
				noDO: `DO${new Date().getFullYear()}-${(Object.keys(dataTracking).length + 1).toString().padStart(4, '0')}`,
				nim: '',
				nama: '',
				ekspedisi: '',
				paket: '',
				tanggalKirim: '',
				status: '',
				total: '',
				perjalanan: [],
			},
			errors: {},
			//static
			pengirimanList,
			paket,
			dataTracking
		}
	}
})

app.mount("#app")