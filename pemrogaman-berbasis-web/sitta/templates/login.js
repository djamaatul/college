const LoginTemplate = {
	props: ['store'],
	emits: ['navigate', 'onLoginSuccess'],
	components: {
		ModalRegister,
		ModalForgot
	},
	data() {
		return {
			email: '',
			password: '',
			loading: false,
			validation: '',
			styles: {
				"title": {
					display: "flex",
					flexDirection: "column",
					alignItems: "center",
					gap: "10px"
				},
				"title_h1": {
					fontSize: "xx-large",
					fontWeight: "bolder",
					color: "var(--primary)"
				},
				"title_p": { color: "grey" }
			}
		}
	},
	methods: {
		async handleLogin() {
			try {
				this.loading = true;

				await wait(1000);

				const user = this.$props.store.dataPengguna.find(user => user.email.toLowerCase() === this.email.toLowerCase().trim())

				if (!user) return this.validation = 'Akun tidak ditemukan';
				if (user.password !== this.password) return this.validation = 'Email / Password yang anda masukkan salah';

				localStorage.setItem('user', JSON.stringify(user))

				this.$emit('navigate', '/')
				this.$emit('onLoginSuccess', user)

			} catch (error) { } finally {
				this.loading = false;
			}
		},
		handleForgot() {
			this.$refs.modalForgot.show();
		},
		handleRegister() {
			this.$refs.modalRegister.show();
		}
	},
	template: `
		<section class="container">
			<div :style="styles.title">
				<h1 :style="styles.title_h1">SITTA</h1>
				<p :style="styles.title_p">Sistem Informasi Tracking & Distribusi	Bahan Ajar</p>
			</div>
			<form class="form">
					<h1>Selamat Datang</h1>
					<input type="email" name="email" placeholder="Email" class="input" required v-model="email">
					<input type="password" name="password" placeholder="Password" class="input" required v-model="password">
					<a href="#" @click="handleForgot" style="text-align: right;">Lupa password ?</a>
					<button @click.prevent="handleLogin" :disabled="loading || !email || !password">Masuk</button>
					<p v-text="validation" class="validation-error"></p>
					<a href="#" @click="handleRegister" style="text-align: center;">Belum punya akun?</a>
			</form>
			<modal-forgot ref="modalForgot"></modal-forgot>
			<modal-register ref="modalRegister"></modal-register>
		</section>
	`
}