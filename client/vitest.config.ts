import { defineConfig } from 'vitest/config';
import angular from '@analogjs/vite-plugin-angular';

export default defineConfig({
  plugins: [angular()],
  test: {
    environment: 'happy-dom',
    globals: true,
    setupFiles: ['src/test.ts'],
  },
});
