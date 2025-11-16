export const wait = (until) => {
	return new Promise(r => {
		setTimeout(r, until)
	})
}