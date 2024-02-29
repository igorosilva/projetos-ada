import * as Pokemon from './pokemonFunctions.js';

const searchInput = document.getElementById('input__search');
const searchButton = document.getElementById('search__button');

searchInput.addEventListener('keypress', (event) => {
    const pokemonInput = searchInput.value.toLowerCase();

    if (event.key === 'Enter') {
        Pokemon.searchPokemon(pokemonInput);
    }
});

searchButton.addEventListener('click', () => {
    const pokemonInput = searchInput.value.toLowerCase();

    Pokemon.searchPokemon(pokemonInput);
});

window.addEventListener('DOMContentLoaded', () => {
    searchInput.focus();
});