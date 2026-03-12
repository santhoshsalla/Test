import { test, expect } from '@playwright/test';
import { chromium } from 'playwright';

test('EPAM: navigate to Client Work via Services', async () => {
  const browser = await chromium.launch({ headless: false });
  const context = await browser.newContext();
  const page = await context.newPage();

  // 1) Navigate to EPAM home
  await page.goto('https://www.epam.com/', { waitUntil: 'networkidle' });

  // 1.a) Dismiss cookie banner if present
  const acceptBtn = page.getByRole('button', { name: /accept all|accept cookies|agree/i });
  if (await acceptBtn.count() > 0 && await acceptBtn.isVisible().catch(() => false)) {
    try { await acceptBtn.click(); } catch (e) { /* ignore */ }
  }

  // 2) Select "Services" from the header
  const services = page.getByRole('link', { name: /^Services$/i }).first();
  await expect(services).toBeVisible({ timeout: 10000 });
  await services.click();
  await page.waitForLoadState('networkidle');

  // 3) Click "Explore Our Client Work"
  const explore = page.getByRole('link', { name: /Explore Our Client Work/i }).first();
  if (await explore.count() > 0 && await explore.isVisible().catch(() => false)) {
    await explore.click();
  } else {
    // fallback
    const fallback = page.locator('text=Explore Our Client Work');
    if (await fallback.count() > 0) {
      await fallback.click();
    } else {
      throw new Error('Explore Our Client Work link not found');
    }
  }
  await page.waitForLoadState('networkidle');

  // 4) Verify "Client Work" text is visible
  const clientWork = page.getByText(/Client Work/i);
  await expect(clientWork).toBeVisible({ timeout: 10000 });

  await browser.close();
});
