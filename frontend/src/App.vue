<script lang="ts">
import { RouterLink, RouterView } from 'vue-router'

</script>

<template>
  <header>

    <div class="wrapper">
      <div>
        <h1>Transform and grow in digital</h1>
        <h3>
          Uma interface bem básica apenas para testar todos os endpoints da aplicação springboot! Se aventure...
        </h3>
      </div>
      <nav>
        <RouterLink to="/">Transações</RouterLink>
        <RouterLink to="/cadastro">Cadastrar</RouterLink>
        <RouterLink to="/tabelas">Acesse os Dados</RouterLink>
      </nav>

    </div> 

  </header>

  <RouterView />
</template>

<style scoped>
@import "./assets/base.css";

header {
  line-height: 1.5;
  height: 100%;
  position: initial;
  text-align: center;
  transition: transform 2s linear;
  padding-bottom: 100px;
  margin: 0 0;
  padding-top: 20px;
  background-image: url("./assets/logo.png");
  background-repeat: no-repeat;
  background-size: 30%;
  background-position: right;
}

nav {
  width: 100%;
  font-size: 14px;
  text-align: center;
  margin-top: 2rem;
  margin-left: 10rem;
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

nav a {
  display: inline-block;
  padding: 1rem 4.5rem;
  background-color: var(--laranja-light);
  border-radius: 5px;
  color: #fff;
  margin: 25px;
}

nav a:hover {
  background-color: #F3C653;
}

nav a:first-of-type {
  border: 0;
}

h1 {
  font-weight: 600;
  font-size: 2.6rem;
  top: -10px;
  padding-top: 15px;
  color: black;
}

h3 {
  padding-top: 5px;
  font-size: 1.2rem;
  color: black;
}

h2 {
  padding-top: 20px;
  text-align: center;
  opacity: 0.8;
  font-size: 1rem;
  color: black;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }
  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
  }
}
</style>
