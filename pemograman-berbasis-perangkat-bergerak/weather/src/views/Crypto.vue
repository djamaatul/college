<template>
  <ion-page>
    <ion-content>
      <ion-refresher slot="fixed" :pull-factor="0.5" :pull-min="100" :pull-max="200"
        @ionRefresh="handleRefresh($event)">
        <ion-refresher-content></ion-refresher-content>
      </ion-refresher>
      <ion-card v-for="value in data">
        <ion-row>
          <ion-card-header>
            <ion-card-subtitle>
              <span>Rank</span>
              <h2>{{ value.rank }}</h2>
            </ion-card-subtitle>
          </ion-card-header>

          <ion-card-header>
            <ion-card-subtitle>
              <span>{{ value.name }}</span>
              <h2>{{ value.symbol }}</h2>
            </ion-card-subtitle>
          </ion-card-header>

          <ion-card-header>
            <ion-card-subtitle>
              <span>USD</span>
              <h2>{{ value.price_usd }}</h2>
            </ion-card-subtitle>
          </ion-card-header>
        </ion-row>
      </ion-card>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { IonPage, IonContent, type RefresherCustomEvent } from '@ionic/vue';
import { IonCard, IonRow, IonCardHeader, IonCardSubtitle, IonRefresher, IonRefresherContent } from '@ionic/vue';

import { onMounted, ref } from 'vue';

type ICrypto = {
  csupply: string,
  id: string,
  market_cap_usd: string,
  msupply: string,
  name: string,
  nameid: string,
  percent_change_1h: string,
  percent_change_24h: string,
  percent_change_7d: string,
  price_btc: string,
  price_usd: string,
  rank: number,
  symbol: string,
  tsupply: string,
  volume24: number,
  volume24a: number
}

const data = ref<ICrypto[]>([]);

async function getCryptoList() {
  try {
    const response = await fetch(`https://api.coinlore.net/api/tickers/`);

    const json = await response.json();

    data.value = json.data;
  } catch { }
}

async function handleRefresh(event: RefresherCustomEvent) {
  await getCryptoList();
  event.target.complete();
}

onMounted(() => {
  getCryptoList();
})

</script>
