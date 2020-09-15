CREATE TABLE items () {
    id SERIAL PRIMARY KEY,
    description TEXT,
    created TIMESTAMP,
    done BOOLEAN DEFAULT FALSE,
    user_id int not null references users(id)
}

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name TEXT,
    email TEXT,
    password TEXT
)