export class Pokemon {
    id;
    sprites;
    name;
    types;
    abilities;

    constructor(id, sprites, name, types, abilities) {
        this.id = id
        this.sprites = sprites;
        this.name = name;
        this.types = types;
        this.abilities = abilities;
    }
}