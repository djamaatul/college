const ModalForgot = {
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
			<dialog :open="open" @click="close">
					<form class="form">
							<h1>Lupa Password</h1>
							<input type="email" name="email" placeholder="Email" class="input" required>
							<button type="submit">Kirim</button>
					</form>
			</dialog>
	`
}