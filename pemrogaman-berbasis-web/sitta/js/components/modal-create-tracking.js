const ModalCreateTracking = {
	mounted() {
		this.dataTracking = BahanAjar.dataTracking;
		this.pengirimanList = BahanAjar.pengirimanList;
		this.paket = BahanAjar.paket;
		this.resetForm();
	},
	data() {
		return {
			open: false,
			form: {
				action: 'I',
				noDO: '',
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
			pengirimanList: [],
			paket: [],
			dataTracking: {}
		}
	},
	methods: {
		show() {
			this.open = true
		},
		close(e) {
			if (e?.target?.tagName !== "DIALOG") return;
			this.open = false
		},
		resetForm() {
			this.form = {
				action: 'I',
				noDO: `DO${new Date().getFullYear()}-${(Object.keys(this.dataTracking).length).toString().padStart(4, '0')}`,
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
						this.open = false;
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
						this.open = false
					}
				}
			}
		}
	},
	template: `
		<dialog :open="open"  @click="close">
				<form class="form" @submit.prevent="save">
						<h1>{{ form.action == 'I' ? 'Tambah Data' : 'Ubah Data' }}</h1>
						<input type="text" placeholder="No DO" class="input" v-model="form.noDO" required disabled>
						<p class="text-error" v-if="errors.noDO" v-text="errors.noDO"></p>
						<input type="text" placeholder="NIM" class="input" v-model="form.nim" required>
						<input type="text" placeholder="Nama" class="input" v-model="form.nama" required>
						<select v-model="form.ekspedisi" required>
								<option value="">-- Pilih Ekspedisi --</option>
								<option v-for="ekspedisi in pengirimanList" :value="ekspedisi.kode">{{ ekspedisi.nama }}</option>
						</select>
						<select v-model="form.paket" required>
								<option value="">-- Pilih Paket --</option>
								<option v-for="paket in paket" :value="paket.kode">{{ paket.nama }}</option>
						</select>
						<p v-if="paket.find(item => item.kode == form.paket)">Isi : {{ paket.find(item => item.kode == form.paket).isi.join(', ') }}</p>
						<p v-if="paket.find(item => item.kode == form.paket)">Harga : {{ paket.find(item => item.kode == form.paket).harga }}</p>
						<input type="date" placeholder="Tanggal Kirim" class="input" v-model="form.tanggalKirim" required>
						<button type="submit">Kirim</button>
				</form>
		</dialog>
	`
}