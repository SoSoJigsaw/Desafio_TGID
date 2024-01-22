<script lang="ts">
import { RouterLink, RouterView } from 'vue-router'

</script>

<template>
  <header>

    <div class="wrapper">
      <h3>
        Uma interface bem básica apenas para testar todos os endpoints da aplicação springboot! Se eventure...
      </h3>

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
  max-height: 100vh;
  position: initial;
  text-align: center;
  transition: transform 2s linear;
  margin: 50px;
}

nav {
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
  display: flex;
  align-items: center;
  justify-content: center;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border: 1px solid #1781E3;
  background-color: #1781E3;
  border-radius: 5px;
  color: #fff;
  margin: 25px;
  padding: 10px;
}

nav a:hover {
  background-color: #F3C653;
}

nav a:first-of-type {
  border: 0;
}

h1 {
  font-weight: 500;
  font-size: 2.6rem;
  top: -10px;
  padding-top: 15px;
  color: #0916A3;
}

h3 {
  padding-top: 5px;
  font-size: 1.2rem;
  color: #0916A3;
}

h2 {
  padding-top: 20px;
  text-align: center;
  opacity: 0.8;
  font-size: 1rem;
  color: #0916A3;
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
