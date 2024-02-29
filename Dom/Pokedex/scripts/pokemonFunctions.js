const searchedPokemonContainer = document.getElementById('pokemon-search-container');
const favoritesPokemonsContainer = document.getElementById('favorites-container');
const closeModalButton = document.querySelector('.close');
const modal = document.getElementById('modal');

closeModalButton.addEventListener('click', () => {
    modal.style.display = 'none';
});

function displayModal() {
    modal.style.display = 'block';
}

export async function fetchPokemon(name) {
    if (name !== '') {
        try {
            const response = await fetch(`https://pokeapi.co/api/v2/pokemon/${name}`);

            if (!response.ok) {
                throw new Error('Unable to find the Pokémon');
            }

            if (response.status === 404) {
                throw new Error('Pokémon not found');
            }

            const data = await response.json();

            return data;
        } catch (error) {
            console.error(error);
            displayModal();
        }
    }
}

function capitalizeFirstLetter(word) {
    return word.charAt(0).toUpperCase() + word.slice(1);
}

function updateSearchedPokemonDisplay(pokemon) {
    const searchedPokemonInfo = document.querySelector(`[data-pokemon-id="${pokemon.id}"] .pokemon-title`);

    if (searchedPokemonInfo) {
        const favoriteIcon = searchedPokemonInfo.querySelector('span');

        if (isFavorite(pokemon)) {
            favoriteIcon.classList.remove('uil--favorite');
            favoriteIcon.classList.add('uim--favorite');
        } else {
            favoriteIcon.classList.remove('uim--favorite');
            favoriteIcon.classList.add('uil--favorite');
        }
    }
}

function toggleFavorite(pokemon) {
    const favorites = JSON.parse(localStorage.getItem('favorites')) || [];
    const index = favorites.findIndex(favorite => favorite.id === pokemon.id);

    if (index === -1) {
        favorites.push(pokemon);
    } else {
        favorites.splice(index, 1);
    }

    localStorage.setItem('favorites', JSON.stringify(favorites));

    updateSearchedPokemonDisplay(pokemon);
}

function isFavorite(pokemon) {
    const favorites = JSON.parse(localStorage.getItem('favorites')) || [];

    return favorites.some(favorite => favorite.id === pokemon.id);
}

function displayFavoritePokemon() {
    favoritesPokemonsContainer.innerHTML = '';

    const favorites = JSON.parse(localStorage.getItem('favorites')) || [];

    favorites.forEach(pokemon => {
        const pokemonInfo = displayPokemon(pokemon);
        favoritesPokemonsContainer.appendChild(pokemonInfo);

        updateSearchedPokemonDisplay(pokemon);
    });
}

window.addEventListener('DOMContentLoaded', displayFavoritePokemon);

function displayPokemon(pokemon) {
    const pokemonInfoContainer = document.createElement('div');
    pokemonInfoContainer.classList.add('pokemon-info-container');
    pokemonInfoContainer.setAttribute('data-pokemon-id', pokemon.id);

    const favoriteClass = isFavorite(pokemon) ? 'uim--favorite' : 'uil--favorite';

    pokemonInfoContainer.innerHTML = `
        <img src="${pokemon.sprites.front_default}">
        <div class="pokemon-title">
            <h2 class="pokemon-name">${capitalizeFirstLetter(pokemon.name)}</h2>
            <span class="${favoriteClass}"></span>
        </div>
        <div class="pokemon-info">
            <div class="pokemon-type-container">
                ${pokemon.types.map(type => `<p class="pokemon-type">Type: ${capitalizeFirstLetter(type.type.name)}</p>`).join('')}
            </div>
            <div class="pokemon-skills">
                <h3 class="skills">Skills</h3>
                <ul class="pokemon-skills">
                    ${pokemon.abilities.map(ability => `<li class="skill">${capitalizeFirstLetter(ability.ability.name)}</li>`).join('')}
                </ul>
            </div>
        </div>
    `;

    const favoriteIcon = pokemonInfoContainer.querySelector('.pokemon-title span');
    favoriteIcon.addEventListener('click', () => {
        toggleFavorite(pokemon);
        displayFavoritePokemon();
    });

    return pokemonInfoContainer;
}


export async function searchPokemon(name) {
    const pokemonData = await fetchPokemon(name);

    searchedPokemonContainer.innerHTML = '';

    const pokemonElement = displayPokemon(pokemonData);
    searchedPokemonContainer.appendChild(pokemonElement);

    updateSearchedPokemonDisplay(pokemonData);
}