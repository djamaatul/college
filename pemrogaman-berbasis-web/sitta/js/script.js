function getDateTime() {
	return new Intl.DateTimeFormat("id-ID", { dateStyle: 'full', timeStyle: 'long' }).format(Date.now())
}

Vue.createApp({
	mounted() {
		setInterval(() => {
			this.time = getDateTime()
		}, 1000);
	},
	data() {
		return {
			time: getDateTime()
		}
	},
	methods: {
		logout() {
			const confirmed = confirm("Anda yakin untuk keluar?");
			if (!confirmed) return;
			localStorage.removeItem("user")
			window.open('./index.html', '_self')
		}
	}
}).mount("nav");

const allDropdownMenu = document.querySelectorAll('nav > ul a + ul')

allDropdownMenu.forEach(ul => {
	const a = ul.previousElementSibling
	a.innerText += '▼'
	a.onclick = function () {
		a.classList.toggle('open')
		if (a.classList.contains('open')) {
			a.innerText = a.innerText.replace('▼', '▲')
		} else {
			a.innerText = a.innerText.replace('▲', '▼')
		}
	}
})