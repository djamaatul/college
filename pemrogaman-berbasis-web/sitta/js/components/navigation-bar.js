const navigations = [
	{
		title: 'Dashboard',
		path: '/'
	},
	{
		title: 'Informasi Bahan Ajar',
		path: '/stok'
	},
	{
		title: 'Tracking pengiriman',
		path: '/tracking'
	},
	{
		title: 'Laporan',
		path: '/laporan',
		child: [
			{
				title: 'Monitoring Progress DO Bahan Ajar',
				path: '/monitoring'
			},
			{
				title: 'Rekap Bahan Ajar',
				path: '/rekap'
			}
		]
	},
	{
		title: 'Histori Transaksi Bahan Ajar'
	}
]

const NavigationBar = {
	emits: ['navigate'],
	mounted() {
		setInterval(() => {
			this.time = this.getDateTime()
		}, 1000);
	},
	data() {
		return {
			time: this.getDateTime(),
			path: '/',
			navigations
		}
	},
	methods: {
		navigate(nav) {
			this.path = nav.path;
			this.$emit('navigate', nav.path);
		},
		getDateTime() {
			return new Intl.DateTimeFormat("id-ID", { dateStyle: 'full', timeStyle: 'long' }).format(Date.now())
		},
		logout() {
			const confirmed = confirm("Anda yakin untuk keluar?");
			if (!confirmed) return;
			localStorage.removeItem("user")
			this.$emit('navigate', '/login')
		}
	},
	template: `
		<nav>
				<ul>
						<li v-for="nav in navigations">
							<a @click.prevent="()=> navigate(nav)" :class="{active: path === nav.path}">
								{{nav.title}}
							</a>
						</li>
						<span id="time">{{time}}</span>
						<li><a id="logout" @click="logout">Logout</a></li>
				</ul>
		</nav>
	`
}