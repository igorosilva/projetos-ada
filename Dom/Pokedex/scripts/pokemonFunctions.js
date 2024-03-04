import { Pokemon } from './classes/Pokemon.js';

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
    if (typeof word === 'string' && word.length > 0) {
        return word.charAt(0).toUpperCase() + word.slice(1);
    } else {
        return '';
    }
}

function updateSearchedPokemonDisplay(pokemonData) {
    const searchedPokemonInfo = document.querySelector(`[data-pokemon-id="${pokemonData.id}"] .pokemon-title`);

    if (searchedPokemonInfo) {
        const favoriteIcon = searchedPokemonInfo.querySelector('span');

        if (isFavorite(pokemonData)) {
            favoriteIcon.classList.remove('uil--favorite');
            favoriteIcon.classList.add('uim--favorite');
        } else {
            favoriteIcon.classList.remove('uim--favorite');
            favoriteIcon.classList.add('uil--favorite');
        }
    }
}

function toggleFavorite(pokemonData) {
    const favorites = JSON.parse(localStorage.getItem('favorites')) || [];
    const index = favorites.findIndex(favorite => favorite.id === pokemonData.id);

    if (index === -1) {
        const favoritePokemon = new Pokemon(
            pokemonData.id,
            pokemonData.sprites.front_default,
            pokemonData.name,
            pokemonData.height,
            pokemonData.weight,
            pokemonData.types,
            pokemonData.abilities.map(ability => ability.ability.name)
        );
        favorites.push(favoritePokemon);
    } else {
        favorites.splice(index, 1);
    }

    localStorage.setItem('favorites', JSON.stringify(favorites));

    updateSearchedPokemonDisplay(pokemonData);
    displayFavoritePokemon();
}

function isFavorite(pokemonData) {
    const favorites = JSON.parse(localStorage.getItem('favorites')) || [];
    return favorites.some(favorite => favorite.id === pokemonData.id);
}

function displayFavoritePokemon() {
    favoritesPokemonsContainer.innerHTML = '';

    const favorites = JSON.parse(localStorage.getItem('favorites'));

    favorites.forEach(pokemon => {
        console.log("informaçoes pokemon localstorage: ", pokemon)

        const pokemonInfo = displayPokemon(pokemon);

        console.log(pokemonInfo)
        favoritesPokemonsContainer.appendChild(pokemonInfo);
    });
}

window.addEventListener('DOMContentLoaded', () => {
    displayFavoritePokemon();
});

function displayPokemon(pokemon) {
    const pokemonInfoContainer = document.createElement('div');
    pokemonInfoContainer.classList.add('pokemon-info-container');
    pokemonInfoContainer.setAttribute('data-pokemon-id', pokemon.id);

    const favoriteClass = isFavorite(pokemon) ? 'uim--favorite' : 'uil--favorite';

    let typeList = '';
    if (Array.isArray(pokemon.types)) {
        typeList = pokemon.types.map(type => {
            if (type && type.type && type.type.name) {
                return `<li class="pokemon-type">${capitalizeFirstLetter(type.type.name)}</li>`;
            } else {
                return '';
            }
        }).join('');
    }

    let abilityList = '';
    if (Array.isArray(pokemon.abilities)) {
        abilityList = pokemon.abilities.map(skill => {
            if (skill && skill.ability && skill.ability.name) {
                return `<li class="skill">${capitalizeFirstLetter(skill.ability.name)}</li>`;
            } else if (skill && skill.ability) {
                return `<li class="skill">${capitalizeFirstLetter(skill.ability)}</li>`;
            } else if (skill) {
                return `<li class="skill">${capitalizeFirstLetter(skill)}</li>`;
            } else {
                return '';
            }
        }).join('');
    }

    const adjustedHeight = pokemon.height / 10;
    const adjustedWeight = pokemon.weight / 10;

    pokemonInfoContainer.innerHTML = `
        <img src="${pokemon.sprites.front_default || pokemon.sprites}">
        <div class="pokemon-title">
            <h2 class="pokemon-name">${capitalizeFirstLetter(pokemon.name)}</h2>
            <span class="${favoriteClass}"></span>
        </div>
        <div class="pokemon-info">
            <div class="pokemon-height-weight">
                <p>Height: ${adjustedHeight}m</p>
                <p>Weight: ${adjustedWeight}kg</p>
            </div>
            <div class="pokemon-type-container">
                <h3>Type</h3>
                <ul class="pokemon-types">
                    ${typeList}
                </ul>
            </div>
            <div class="pokemon-skills">
                <h3 class="skills">Skills</h3>
                <ul class="pokemon-skills">
                    ${abilityList}
                </ul>
            </div>
        </div>
    `;

    const favoriteIcon = pokemonInfoContainer.querySelector('.pokemon-title span');
    favoriteIcon.addEventListener('click', () => {
        toggleFavorite(pokemon);
    });

    return pokemonInfoContainer;
}

export async function searchPokemon(name) {
    const pokemonData = await fetchPokemon(name);

    if (pokemonData) {
        searchedPokemonContainer.innerHTML = '';
        const pokemonElement = displayPokemon(pokemonData);
        searchedPokemonContainer.appendChild(pokemonElement);
        updateSearchedPokemonDisplay(pokemonData);
    }
}