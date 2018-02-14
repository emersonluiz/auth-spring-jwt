import { StockWebPage } from './app.po';

describe('stock-web App', () => {
  let page: StockWebPage;

  beforeEach(() => {
    page = new StockWebPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
