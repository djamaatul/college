const { createApp } = Vue;
import { dataTracking } from './data.js'
import { wait } from './wait.js'

const app = createApp({
	mounted() {
		this.dataTracking = dataTracking;
	},
	methods: {
		async tracking() {
			this.loading = true;

			const dataTrack = this.dataTracking[this.inputNomorDO.toLowerCase().trim()]

			if (!dataTrack) {
				this.loading = false;
				return window.alert('No. DO / Billing tidak ditemukan')
			}
			await wait(1000);

			this.dataTrack = dataTrack;
		}
	},
	data() {
		return {
			inputNomorDO: '',
			loading: false,
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
			}
		}
	}
})

app.mount("#app")