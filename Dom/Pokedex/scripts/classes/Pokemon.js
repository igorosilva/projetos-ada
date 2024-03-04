export class Pokemon {
    id;
    sprites;
    name;
    height;
    weight;
    types;
    abilities;

    constructor(id, sprites, name, height, weight, types, abilities) {
        this.id = id
        this.sprites = sprites;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.types = types;
        this.abilities = abilities;
    }
}