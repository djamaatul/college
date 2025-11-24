const app = Vue.createApp({
	components: {
		'/': DashboardTemplate,
		'/login': LoginTemplate,
		'/stok': StokTemplate,
		'/tracking': TrackingTemplate
	},
	async mounted() {
		await wait(1000);
		this.data = BahanAjar;

		const user = window.localStorage.getItem('user');

		if (user) {
			this.session = JSON.parse(user);
			this.navigate('/')
		}
		else this.navigate('/login')
	},
	data: () => ({
		path: '/',
		data: null,
		session: null
	}),
	methods: {
		navigate(path) {
			if (path === '/login') {
				this.session = null;
			}
			this.path = path;
		}
	}
});

app.component('appNavigationBar', NavigationBar)
app.component('appFooter', Footer)

app.mount('#app');