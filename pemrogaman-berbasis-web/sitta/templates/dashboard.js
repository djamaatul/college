const DashboardTemplate = {
	props: ['store'],
	mounted() {
		this.hour = new Date().getHours();
		this.bahanAjar = this.$props.store.dataBahanAjar.map(r => {
			return {
				...r,
				color: this.getColor()
			}
		});
		this.tracking = this.$props.store.dataTracking ?? [];

		const user = window.localStorage.getItem('user');

		if (user) {
			const data = JSON.parse(user);
			this.name = `<span style="margin-left: 10px; color: var(--primary)">${data.nama}</span>`
		}
	},
	methods: {
		getColor() {
			const getHex = () => Math.floor(Math.random() * (200 - 100 + 1) + 100);
			return `rgba(${getHex()},${getHex()},${getHex()},1)`
		}
	},
	watch: {
		bahanAjar(next) {
			this.maxStockBahanAjar = Math.max(...next.map(r => r.qty));
		},
		tracking(next) {
			const count = Object.values(next).reduce((acc, curr) => {
				const prev = acc[curr.status];
				acc[curr.status] = {
					total: (prev?.total || 0) + 1,
					color: this.getColor()
				}
				return acc;
			}, {});

			this.trackingStatusCount = count;
		}
	},
	computed: {
		greeting() {
			if (this.hour >= 10 && this.hour < 14) return 'Selamat siang'
			if (this.hour >= 14) return 'Selamat sore'
			if (this.hour >= 18) return 'Selamat malam'
			return 'Selamat pagi';
		}
	},
	data: () => {
		return {
			hour: "",
			name: "",
			bahanAjar: [],
			maxStockBahanAjar: 0,
			tracking: {},
			trackingStatusCount: {},
			styles: {
				dashboardContainer: {
					display: 'flex',
					gap: '10px',
				},
				dashboardItem: {
					display: "flex",
					flexDirection: "column",
					flex: 1,
					padding: "10px",
					alignItems: "center",
					border: "1px solid var(--secondary)"
				}
			}
		}
	},
	template: `
			<section>
				<h1 style="margin: 20px 0px; font-weight: bold;">Dashboard Aplikasi SITTA</h1>
				<p style="margin: 20px 0px; font-weight: bold;">
					<span v-text="greeting"></span>
					<span v-html="name"></span>
				</p>
			</section>
			<section :style="styles.dashboardContainer">
				<section :style="styles.dashboardItem">
					<h2>Stok Bahan Ajar</h2>
					<svg xmlns="https://www.w3.org/2000/svg" style="min-height: 400px" >
						<g v-for="(item, index) in bahanAjar">
							<rect :x="(40 * (index + 1))" :y="maxStockBahanAjar - (item.qty) + 10" width="20" :height="item.qty"  :fill="item.color" :class="index" />
							<text :x="(40 * (index + 1))" :y="maxStockBahanAjar + 30" fill="black">{{item.qty}}</text>
						</g>
						<g v-for="(item, index) in bahanAjar">
							<rect :x="0" :y="maxStockBahanAjar + ((index + 1) * 20) + 50" width="10" height="10" :fill="item.color">{{item.judul}}</rect>
							<text :x="20" :y="maxStockBahanAjar + ((index + 1) * 20) + 60" fill="black">{{item.judul}}</text>
						</g>
					</svg>
				</section>
				<section :style="styles.dashboardItem">
					<h2>Pengiriman</h2>
					<svg xmlns="https://www.w3.org/2000/svg" style="min-height: 400px">
						<g v-for="([key, value], index) in Object.entries(trackingStatusCount)">
							<rect :x="(30 * index)" y="40" width="20" :height="value.total * 10" :fill="value.color" :class="index" />
							<text :x="(30 * index) + 5" y="70" fill="black">{{value.total}}</text>
						</g>
						<g v-for="([key, value], index) in Object.entries(trackingStatusCount)">
							<rect :x="0" :y="50 + (index * 20) + 40" width="10" :height="10 * value.total" :fill="value.color"></rect>
							<text :x="20" :y="50 + (index * 20) + 50" fill="black">{{key}}</text>
						</g>
					</svg>
				</section>
			</section>
	`
}