export interface Campaign {
  id: number;
  productId: number;
  name: string;
  keywords: string[];
  bidAmount: number;
  fund: number;
  status: 'ON' | 'OFF';
  town: string;
  radiusKm: number;
}
