import { test, expect } from '@playwright/test';
import { chromium } from 'playwright';

test('EPAM: navigate to Client Work via Services', async () => {
  // Explicitly launch the browser
  const browser = await chromium.launch({ headless: true });
  const context = await browser.newContext();
  const page = await context.newPage();

  // 1) Navigate to EPAM home page
  await page.goto('https://www.epam.com/', { waitUntil: 'networkidle' });

  // Best-effort: dismiss cookie/banner if present
  try {
    const acceptBtn = page.getByRole('button', { name: /accept all|accept cookies|agree/i });
    if (await acceptBtn.count()) {
      await acceptBtn.first().click({ timeout: 3000 }).catch(() => {});
    }
  } catch (e) {
    // ignore
  }

  // 2) Select "Services" from the header menu
  const servicesLink = page.getByRole('link', { name: /^Services$/i }).first();
  await expect(servicesLink).toBeVisible({ timeout: 10000 });
  await servicesLink.click();
  await page.waitForLoadState('networkidle');

  // 3) Click the "Explore Our Client Work" link
  // Try role-based locator first, fallback to text locator
  let clicked = false;
  const exploreRole = page.getByRole('link', { name: /Explore Our Client Work/i }).first();
  if (await exploreRole.count() && await exploreRole.isVisible().catch(() => false)) {
    await exploreRole.click();
    clicked = true;
  } else {
    const exploreText = page.locator('text=Explore Our Client Work').first();
    if (await exploreText.count() && await exploreText.isVisible().catch(() => false)) {
      await exploreText.click();
      clicked = true;
    }
  }

  if (!clicked) {
    throw new Error('Could not find "Explore Our Client Work" link.');
  }

  await page.waitForLoadState('networkidle');

  // 4) Verify that the "Client Work" text is visible on the page
  const clientWorkText = page.getByText(/Client Work/i).first();
  await expect(clientWorkText).toBeVisible({ timeout: 10000 });

  // Close browser
  await browser.close();
});
