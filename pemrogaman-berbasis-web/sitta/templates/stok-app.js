const colorStatus = {
	'Aman': '#61d800',
	'Menipis': '#ff9e22',
	'Kosong': '#e54304'
}

const StokTemplate = {
	emits: [],
	props: ['store'],
	mounted() {
		this.syncData();
		this.kategoriList = this.$props.store.kategoriList;
		this.upbjjList = this.$props.store.upbjjList;
	},
	computed: {
		filterOptions() {
			return ({
				'upbjj': this.$props.store.upbjjList,
				'kategori': this.$props.store.kategoriList
			}[this.filterBy] ?? [])
		}
	},
	watch: {
		sort(val) {
			this.bahanAjar = [...this.bahanAjar].sort((a, b) => {
				if (val === 'asc') return a.judul?.localeCompare(b.judul) || a.qty?.localeCompare(b.qty) || a.harga - b.harga;
				return b.judul?.localeCompare(a.judul) || b.qty?.localeCompare(a.qty) || b.harga - a.harga;
			})
		}
	},
	setup() {

		const bahanAjar = Vue.ref([]);
		const mode = Vue.ref('normal');
		const filter = Vue.ref('');
		const filterBy = Vue.ref('');
		const sort = Vue.ref('asc');

		const filteredBahanAjar = Vue.computed(() => {
			return bahanAjar.value.flatMap((r, i) => {
				let status = 'Kosong';
				if (r.qty >= +r.safety) status = 'Aman';
				else if (r.qty > 0) status = 'Menipis';

				if (filter.value) {
					const exist = r[filterBy.value]?.toLowerCase().includes(filter.value.toLowerCase());
					if (!exist) return [];
				}
				return {
					...r, i,
					error: {},
					safety: +r.safety || 0,
					status,
				};
			})
		})

		return {
			mode,
			bahanAjar,
			colorStatus,
			filteredBahanAjar,
			filterBy,
			filter,
			sort,
			kategoriList: [],
			upbjjList: [],
			currencyFormater: new Intl.NumberFormat("id-ID", { style: "currency", currency: "IDR" }),
			handleAppendData() {
				filter.value = '';
				filterBy.value = '';
				bahanAjar.value = [{ edit: true }, ...bahanAjar.value]
			},
			handleRemove(v) {
				const ok = window.confirm(`Anda yakin untuk menghapus ${v.judul || ''}?`)

				if (!ok) return;

				const result = bahanAjar.value.filter((r) => r.kode !== v.kode);

				localStorage.setItem('dataBahanAjar', JSON.stringify(result))
				bahanAjar.value = result;

				this.syncData();
			},
			handleResetFilter() {
				filter.value = '';
				filterBy.value = '';
			},
			syncData() {
				const version = localStorage.getItem('version');

				if (version !== this.$props.store.releaseVersion) {
					localStorage.setItem('version', this.$props.store.releaseVersion);
					localStorage.setItem('dataBahanAjar', JSON.stringify(this.$props.store.dataBahanAjar))
				};

				const cached = localStorage.getItem('dataBahanAjar')

				if (!bahanAjar.value.length && cached) {
					const dataBahanAjar = JSON.parse(cached)
					bahanAjar.value = dataBahanAjar;
				}
			},
			handleSave(v) {
				const validation = [
					"kode",
					"judul",
					"kategori",
					"upbjj",
					"lokasiRak",
					"harga",
					"qty",
					"safety"].reduce((acc, k) => {
						if (!this.bahanAjar[v.i][k]) {
							acc[k] = "Harus diisi"
						}
						return acc;
					}, {});

				if (Object.keys(validation).length) return this.bahanAjar[v.i].error = validation;
				else this.bahanAjar[v.i].error = {};

				this.bahanAjar[v.i].edit = false;
				localStorage.setItem('dataBahanAjar', JSON.stringify(this.bahanAjar));
			},
			onEnter(i) {
				this.handleSave({ ...this.bahanAjar[i], i })
			}
		}
	},
	template: `
	<section class="filter">
		<div class="filter-section">
			<label>Filter</label>
			<select name="filterList" v-model="filterBy">
				<option value="" disabled>Pilih</option>
				<option value="upbjj">UT Daerah</option>
				<option value="kategori">Kategori mata kuliah</option>
			</select>
			<select name="filterOptions" v-model="filter" v-if="filterBy">
				<option value="" disabled>Pilih</option>
				<option v-for="op in filterOptions">{{op}}</option>
			</select>
		</div>
		<div class="filter-section">
			<label>Urutkan</label>
			<select name="kategori" v-model="sort">
				<option value="asc">A - Z</option>
				<option value="desc">Z - A</option>
			</select>
		</div>
		<button type="button" class="btn-red" @click="handleResetFilter" v-if="filterBy">Atur ulang</button>
	</section>
	<section class="filter">
		<button type="button" @click="handleAppendData">Tambah</button>
	</section>
	<section id="stocks">
		<div v-for="(v) in filteredBahanAjar" class="stock" v-if="bahanAjar.length">
			<button class="remove" @click="()=> handleRemove(bahanAjar[v.i])">
				X
			</button>
			<form class="stock-detail">
				<p>Kode Buku</p>
				<input type="text" v-on:keyup.enter="()=> onEnter(v.i)" name="kode" v-model="bahanAjar[v.i].kode"
					:disabled="!v.edit" :class="{'input-error': bahanAjar[v.i].error?.kode}">
				<span class="error">{{bahanAjar[v.i].error?.kode}}</span>
				<p>Judul</p>
				<input type="text" v-on:keyup.enter="()=> onEnter(v.i)" name="judul" v-model="bahanAjar[v.i].judul"
					:disabled="!v.edit" :class="{'input-error': bahanAjar[v.i].error?.judul}">
				<span class="error">{{bahanAjar[v.i].error?.judul}}</span>
				<p>Kategori</p>
				<select name="kategori" v-model="bahanAjar[v.i].kategori" :disabled="!v.edit"
					:class="{'input-error': bahanAjar[v.i].error?.kategori}">
					<option v-for="op in kategoriList">{{op}}</option>
				</select>
				<span class="error">{{bahanAjar[v.i].error?.kategori}}</span>
				<p>UT Daerah</p>
				<select name="upbjj" v-model="bahanAjar[v.i].upbjj" :disabled="!v.edit"
					:class="{'input-error': bahanAjar[v.i].error?.upbjj}">
					<option v-for="op in upbjjList">{{op}}</option>
				</select>
				<span class="error">{{bahanAjar[v.i].error?.upbjj}}</span>
				<p>Lokasi Rak</p>
				<input type="text" v-on:keyup.enter="()=> onEnter(v.i)" name="lokasiRak" v-model="bahanAjar[v.i].lokasiRak"
					:disabled="!v.edit" :class="{'input-error': bahanAjar[v.i].error?.lokasiRak}">
				<span class="error">{{bahanAjar[v.i].error?.lokasiRak}}</span>
				<p>Harga</p>
				<input type="text" v-on:keyup.enter="()=> onEnter(v.i)" name="harga" v-model="bahanAjar[v.i].harga"
					:disabled="!v.edit" :class="{'input-error': bahanAjar[v.i].error?.harga}" v-if="v.edit">
				<input v-else type="text" v-on:keyup.enter="()=> onEnter(v.i)"
					:value="currencyFormater.format(bahanAjar[v.i].harga)" disabled></input>
				<span class="error">{{bahanAjar[v.i].error?.harga}}</span>
				<p>Stok</p>
				<input type="text" v-on:keyup.enter="()=> onEnter(v.i)" name="qty" v-model="bahanAjar[v.i].qty"
					:disabled="!v.edit" :class="{'input-error': bahanAjar[v.i].error?.qty}">
				<span class="error">{{bahanAjar[v.i].error?.qty}}</span>
				<p>Safety Stok</p>
				<div class="stok">
					<input type="text" v-on:keyup.enter="onEnter" name="safety" v-model="bahanAjar[v.i].safety" :disabled="!v.edit"
						:class="{'input-error': bahanAjar[v.i].error?.safety}">
					<span class="status-stok" v-text="v.status"
						:style="{color: 'rgba(0,0,0,.5)', backgroundColor: colorStatus[v.status] }"></span>
				</div>
				<span class="error">{{bahanAjar[v.i].error?.safety}}</span>
				<p>Catatan</p>
				<div v-if="v.catatanHTML || v.edit">
					<textarea class="catatan" v-model="bahanAjar[v.i].catatanHTML" v-if="v.edit"></textarea>
					<div class="catatan" v-html="bahanAjar[v.i].catatanHTML" v-if="!v.edit"></div>
				</div>
				<span v-else>-</span>
			</form>
			<button type="submit" @click.prevent="()=> handleSave({...bahanAjar[v.i], i: v.i})"
				v-if="bahanAjar[v.i].edit">Simpan</button>
			<button type="button" @click="()=> bahanAjar[v.i].edit = true" class="btn-secondary"
				v-if="!bahanAjar[v.i].edit">Ubah</button>
		</div>
		<div v-else>
			Data Tidak ditemukan
		</div>
	</section>
	`
}