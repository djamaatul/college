const ModalRegister = {
	props: [],
	data() {
		return {
			open: false
		}
	},
	methods: {
		show() {
			this.open = true
		},
		close(e) {
			if (e?.target?.tagName !== "DIALOG") return;
			this.open = false
		}
	},
	template: `
			<dialog :open="open"  @click="close">
				<form class="form">
						<h1>Daftar</h1>
						<input type="text" name="nama" placeholder="Nama" class="input" required>
						<input type="email" name="email" placeholder="Email" class="input" required>
						<input type="password" name="password" placeholder="Password" class="input" required>
						<input type="password" name="konfirmasiPassword" placeholder="Konfirmasi Password" class="input" required>
						<select name="lokasi" required>
								<option value="UPBJJ">UPBJJ</option>
								<option value="Pusat">Pusat</option>
								<option value="FISIP">FISIP</option>
						</select>
						<button type="submit">Kirim</button>
				</form>
			</dialog>
	`
}