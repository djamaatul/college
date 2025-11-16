const user = window.localStorage.getItem('user');
if (!user) {
	window.open('/', '_self')
}

const allDropdownMenu = document.querySelectorAll('nav > ul a + ul')
const greeting = document.querySelector('#greeting')
const btnLogout = document.querySelector('#btnLogout')

allDropdownMenu.forEach(ul => {
    const a = ul.previousElementSibling
    a.innerText += '▼'
    a.onclick = function(){
        a.classList.toggle('open')
        if(a.classList.contains('open')){
            a.innerText = a.innerText.replace('▼', '▲')
        }else{
            a.innerText = a.innerText.replace('▲', '▼')
        }
    }
})

btnLogout.onclick = function(){
    if(confirm('Yakin ingin logout?')){
        localStorage.removeItem('user')
        window.open('index.html', '_self', null)
    }
}

if(greeting){
		let msg = 'Selamat pagi';
		if (new Date().getHours() >= 10 && new Date().getHours() < 14) {
			msg = 'Selamat siang'
		} 
		if (new Date().getHours() >= 14) {
			msg = 'Selamat sore'
		}

		if (new Date().getHours() >= 18) {
			msg = 'Selamat malam'
		}
		const letter = document.createElement('span')
		letter.innerText = msg

		greeting.appendChild(letter)

		const user = window.localStorage.getItem('user');
		if(user) {
			const name = document.createElement('name')
			name.style.marginLeft = '10px';
			name.style.color = 'var(--primary)';
			const data = JSON.parse(user);
			name.innerText = data.nama;
			greeting.appendChild(name)
		}
}