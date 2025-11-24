const TrackingTemplate = {
	props: ['store'],
	components: {
		ModalCreateTracking
	},
	methods: {
		async tracking() {
			this.loading = true;

			const nomorDO = this.inputNomorDO.toUpperCase().trim();

			const dataTrack = this.$props.store.dataTracking[nomorDO]

			await wait(1000);

			if (!dataTrack) {
				this.loading = false;
				return window.alert('No. DO / Billing tidak ditemukan')
			}

			this.loading = false;

			this.dataTrack = { ...dataTrack, nomorDO };
		},
		tambah() {
			this.$refs.modalCreate.show();
		},
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
			},
		}
	},
	template: `
        <form class="form" id="form-tracking">
            <label>
                <span style="margin: 0px 20px">No. DO / Billing</span>
                <input type="text" v-model="inputNomorDO" placeholder="No. DO / Billing" class="input" required>
            </label>
						<div style="display: flex; gap: 20px; align-self: flex-end;">
							<button style="background-color: green;" type="button" @click="tambah">Tambah</button>
							<button type="button" @click="tracking" :disabled="loading">Lacak</button>
						</div>
        </form>
        <section id="tracking" v-if="dataTrack.nomorDO">
							<section id="detail">
                <div>
                    <div>
                        <span id="nama">{{ dataTrack.nama }}</span>
                        <span id="nomorDO">{{ dataTrack.nim }}</span>
                    </div>
                    <p id="tanggalKirim">{{ dataTrack.tanggalKirim }}</p>
                </div>
                <table>
									<tbody>
                    <tr>
                        <td style="width: 100px;">Ekspedisi</td>
                        <td style="width: 10px;">:</td>
                        <td><span id="ekspedisi">{{ dataTrack.ekspedisi }}</span></td>
                    </tr>
                    <tr>
                        <td>Paket</td>
                        <td>:</td>
                        <td><span id="paket">{{ dataTrack.paket }}</span></td>
                    </tr>
                    <tr>
                        <td>Total</td>
                        <td>:</td>
                        <td><span id="total">{{ dataTrack.total }}</span></td>
                    </tr>
									</tbody>
                </table>
            </section>
        </section>
				<modal-create-tracking ref="modalCreate"></modal-create-tracking>
	`
}